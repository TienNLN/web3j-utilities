package com.tiennln.testaquariux.services.impl;

import com.tiennln.testaquariux.constants.CommonConstant;
import com.tiennln.testaquariux.constants.enums.TradingPair;
import com.tiennln.testaquariux.constants.enums.TransactionType;
import com.tiennln.testaquariux.dtos.payloads.BuyPayload;
import com.tiennln.testaquariux.dtos.payloads.SellPayload;
import com.tiennln.testaquariux.entities.TransactionHistory;
import com.tiennln.testaquariux.entities.UserWallet;
import com.tiennln.testaquariux.exceptions.BaseException;
import com.tiennln.testaquariux.repositories.BookTickerRepository;
import com.tiennln.testaquariux.repositories.TransactionHistoryRepository;
import com.tiennln.testaquariux.repositories.UserRepository;
import com.tiennln.testaquariux.repositories.UserWalletRepository;
import com.tiennln.testaquariux.services.TransactionService;
import com.tiennln.testaquariux.utils.CredentialUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * The type Transaction service.
 *
 * @author TienNLN on 02/04/2023
 */
@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private UserRepository userRepository;
    private UserWalletRepository userWalletRepository;
    private BookTickerRepository bookTickerRepository;

    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    @Transactional
    public void buyToken(BuyPayload payload) throws BaseException {

        if (!payload.getTradingPair().equalsIgnoreCase(TradingPair.ETHUSDT.name())
                && !payload.getTradingPair().equalsIgnoreCase(TradingPair.BTCUSDT.name())) {
            throw new BaseException(100L, "Not support trading pair", HttpStatus.BAD_REQUEST);
        }

        var user = userRepository.findUserByWalletAddress(payload.getWalletAddress());
        var userWallets = userWalletRepository.findAllByUser(user);

        if (!CredentialUtil.decodePrivateKey(user.getPrivateKey()).equalsIgnoreCase(payload.getPrivateKey())) {
            throw new BaseException(102L, "Invalid private key", HttpStatus.BAD_REQUEST);
        }

        if (payload.getTradingPair().equalsIgnoreCase(TradingPair.ETHUSDT.name())) {
            BigDecimal bestAggregatePrice = bookTickerRepository.findById(TradingPair.ETHUSDT.name()).get().getAskPrice();

            var userWalletUsdt = userWallets.stream()
                    .findFirst()
                    .filter(userWallet -> userWallet.getAssetName().equalsIgnoreCase(CommonConstant.USDT_SYMBOL))
                    .orElse(null);

            var currentUsdtValue = userWalletUsdt.getBalance();

            if (currentUsdtValue.compareTo(payload.getValue()) < 0) {
                throw new BaseException(101L, "Not enough money", HttpStatus.BAD_REQUEST);
            }

            var ethValue = payload.getValue().divide(bestAggregatePrice, 10, RoundingMode.HALF_UP);

            var userWalletEth = userWallets.stream()
                    .filter(userWallet -> userWallet.getAssetName().equalsIgnoreCase(CommonConstant.ETH_SYMBOL))
                    .findFirst()
                    .orElse(null);

            if (userWalletEth == null) {
                userWalletEth = UserWallet.builder()
                        .user(user)
                        .assetName(CommonConstant.ETH_SYMBOL)
                        .balance(ethValue)
                        .build();
            } else {
                userWalletEth.setBalance(userWalletEth.getBalance().add(ethValue));
            }

            userWalletRepository.save(userWalletEth);

            var updatedUsdtValue = currentUsdtValue.subtract(payload.getValue());
            userWalletUsdt.setBalance(updatedUsdtValue);
            userWalletRepository.save(userWalletUsdt);

            transactionHistoryRepository.save(TransactionHistory.builder()
                    .createdDate(new Date())
                    .action(TransactionType.BUY.name())
                    .tradingPair(TradingPair.ETHUSDT.name())
                    .transactionValue(ethValue)
                    .user(user)
                    .build());
        } else if (payload.getTradingPair().equalsIgnoreCase(TradingPair.BTCUSDT.name())) {
            BigDecimal bestAggregatePrice = bookTickerRepository.findById(TradingPair.BTCUSDT.name()).get().getAskPrice();

            var userWalletUsdt = userWallets.stream()
                    .findFirst()
                    .filter(userWallet -> userWallet.getAssetName().equalsIgnoreCase(CommonConstant.USDT_SYMBOL))
                    .orElse(null);

            var currentUsdtValue = userWalletUsdt.getBalance();

            if (currentUsdtValue.compareTo(payload.getValue()) < 0) {
                throw new BaseException(101L, "Not enough money", HttpStatus.BAD_REQUEST);
            }

            var btcValue = payload.getValue().divide(bestAggregatePrice, 10, RoundingMode.HALF_UP);

            var userWalletBtc = userWallets.stream()
                    .filter(userWallet -> userWallet.getAssetName().equalsIgnoreCase(CommonConstant.BTC_SYMBOL))
                    .findFirst()
                    .orElse(null);

            if (userWalletBtc == null) {
                userWalletBtc = UserWallet.builder()
                        .user(user)
                        .assetName(CommonConstant.BTC_SYMBOL)
                        .balance(btcValue)
                        .build();
            } else {
                userWalletBtc.setBalance(userWalletBtc.getBalance().add(btcValue));
            }

            userWalletRepository.save(userWalletBtc);

            var updatedUsdtValue = currentUsdtValue.subtract(payload.getValue());
            userWalletUsdt.setBalance(updatedUsdtValue);
            userWalletRepository.save(userWalletUsdt);

            transactionHistoryRepository.save(TransactionHistory.builder()
                    .createdDate(new Date())
                    .action(TransactionType.BUY.name())
                    .tradingPair(TradingPair.BTCUSDT.name())
                    .transactionValue(btcValue)
                    .user(user)
                    .build());
        }
    }

    @Override
    @Transactional
    public void sellToken(SellPayload payload) throws BaseException {

        if (!payload.getTradingPair().equalsIgnoreCase(TradingPair.ETHUSDT.name())
                && !payload.getTradingPair().equalsIgnoreCase(TradingPair.BTCUSDT.name())) {
            throw new BaseException(100L, "Not support trading pair", HttpStatus.BAD_REQUEST);
        }

        var user = userRepository.findUserByWalletAddress(payload.getWalletAddress());
        var userWallets = userWalletRepository.findAllByUser(user);

        if (!CredentialUtil.decodePrivateKey(user.getPrivateKey()).equalsIgnoreCase(payload.getPrivateKey())) {
            throw new BaseException(102L, "Invalid private key", HttpStatus.BAD_REQUEST);
        }

        if (payload.getTradingPair().equalsIgnoreCase(TradingPair.ETHUSDT.name())) {
            BigDecimal bestAggregatePrice = bookTickerRepository.findById(TradingPair.ETHUSDT.name()).get().getBidPrice();

            var userWalletEth = userWallets.stream()
                    .filter(userWallet -> userWallet.getAssetName().equalsIgnoreCase(CommonConstant.ETH_SYMBOL))
                    .findFirst()
                    .orElse(null);

            var currentEthBalance = userWalletEth.getBalance();

            if (currentEthBalance.compareTo(payload.getValue()) < 0) {
                throw new BaseException(101L, "Not enough money", HttpStatus.BAD_REQUEST);
            }

            var usdtValue = payload.getValue().multiply(bestAggregatePrice);

            var userWalletUsdt = userWallets.stream()
                    .filter(userWallet -> userWallet.getAssetName().equalsIgnoreCase(CommonConstant.USDT_SYMBOL))
                    .findFirst()
                    .orElse(null);

            if (userWalletUsdt == null) {
                userWalletUsdt = UserWallet.builder()
                        .user(user)
                        .assetName(CommonConstant.USDT_SYMBOL)
                        .balance(usdtValue)
                        .build();
            } else {
                userWalletUsdt.setBalance(userWalletUsdt.getBalance().add(usdtValue));
            }

            userWalletRepository.save(userWalletUsdt);

            var updatedEthValue = currentEthBalance.subtract(payload.getValue());
            userWalletEth.setBalance(updatedEthValue);
            userWalletRepository.save(userWalletEth);

            transactionHistoryRepository.save(TransactionHistory.builder()
                    .createdDate(new Date())
                    .action(TransactionType.SELL.name())
                    .tradingPair(TradingPair.ETHUSDT.name())
                    .transactionValue(usdtValue)
                    .user(user)
                    .build());
        } else if (payload.getTradingPair().equalsIgnoreCase(TradingPair.BTCUSDT.name())) {
            BigDecimal bestAggregatePrice = bookTickerRepository.findById(TradingPair.BTCUSDT.name()).get().getBidPrice();

            var userWalletBtc = userWallets.stream()
                    .filter(userWallet -> userWallet.getAssetName().equalsIgnoreCase(CommonConstant.BTC_SYMBOL))
                    .findFirst()
                    .orElse(null);

            var currentBtcBalance = userWalletBtc.getBalance();

            if (currentBtcBalance.compareTo(payload.getValue()) < 0) {
                throw new BaseException(101L, "Not enough money", HttpStatus.BAD_REQUEST);
            }

            var usdtValue = payload.getValue().multiply(bestAggregatePrice);

            var userWalletUsdt = userWallets.stream()
                    .findFirst()
                    .filter(userWallet -> userWallet.getAssetName().equalsIgnoreCase(CommonConstant.USDT_SYMBOL))
                    .orElse(null);

            if (userWalletUsdt == null) {
                userWalletUsdt = UserWallet.builder()
                        .user(user)
                        .assetName(CommonConstant.USDT_SYMBOL)
                        .balance(usdtValue)
                        .build();
            } else {
                userWalletUsdt.setBalance(userWalletUsdt.getBalance().add(usdtValue));
            }

            userWalletRepository.save(userWalletUsdt);

            var updatedBtcValue = currentBtcBalance.subtract(payload.getValue());
            userWalletBtc.setBalance(updatedBtcValue);
            userWalletRepository.save(userWalletBtc);

            transactionHistoryRepository.save(TransactionHistory.builder()
                    .createdDate(new Date())
                    .action(TransactionType.SELL.name())
                    .tradingPair(TradingPair.BTCUSDT.name())
                    .transactionValue(usdtValue)
                    .user(user)
                    .build());
        }
    }
}

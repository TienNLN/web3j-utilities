package com.tiennln.testaquariux.services.impl;

import com.tiennln.testaquariux.constants.CommonConstant;
import com.tiennln.testaquariux.dtos.responses.WalletAssetResponse;
import com.tiennln.testaquariux.entities.User;
import com.tiennln.testaquariux.entities.UserWallet;
import com.tiennln.testaquariux.repositories.UserRepository;
import com.tiennln.testaquariux.repositories.UserWalletRepository;
import com.tiennln.testaquariux.services.UsersService;
import com.tiennln.testaquariux.services.Web3Service;
import com.tiennln.testaquariux.utils.CredentialUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * The type Users service.
 *
 * @author TienNLN on 01/04/2023
 */
@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    private UserRepository userRepository;
    private UserWalletRepository userWalletRepository;
    private Web3Service web3Service;

    /**
     * Init data.
     */
    @PostConstruct
    @Async
    public void initData() {
        var user = User.builder()
                .privateKey("OGQ1OTgwN2E1Yzc2YTRhMmJkNzZjYWNkYTY1OTE4YTljNTJkOWQ0NTJlZDFmMTljOTYyNmUyZjMwYjdhNGE1Ng==")
                .walletAddress("0x27d7E67b846233C5E8Fed8a1B9e5588934e23f34")
                .build();

        userRepository.save(user);

        var userWallet = UserWallet.builder()
                .user(user)
                .assetName(CommonConstant.USDT_SYMBOL)
                .balance(new BigDecimal(50000L))
                .build();

        userWalletRepository.save(userWallet);
    }

    @Override
    public String getPrivateKeyByAddress(String address) {
        var user = userRepository.findUserByWalletAddress(address);

        return CredentialUtil.decodePrivateKey(user.getPrivateKey());
    }

    @Override
    public List<WalletAssetResponse> getBalance(String address) {

        var assetBalanceFromBlockchain = web3Service.getWalletBalance(address, this.getPrivateKeyByAddress(address));

        var user = userRepository.findUserByWalletAddress(address);
        var userWallets = userWalletRepository.findAllByUser(user);

        return userWallets.stream().map(userWallet -> {
                            var blockchainAsset = assetBalanceFromBlockchain.stream()
                                    .filter(asset -> asset.getTokenName().equalsIgnoreCase(userWallet.getAssetName()))
                                    .map(asset -> asset.getBalance())
                                    .findFirst().orElse(null);
                            return WalletAssetResponse.builder()
                                    .tokenName(userWallet.getAssetName())
                                    .balance(userWallet.getBalance().add(blockchainAsset != null ? blockchainAsset : new BigDecimal(0L)))
                                    .build();
                        }
                )
                .toList();
    }
}

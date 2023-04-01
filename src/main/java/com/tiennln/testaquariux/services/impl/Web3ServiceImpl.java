package com.tiennln.testaquariux.services.impl;

import com.tiennln.testaquariux.constants.CommonConstant;
import com.tiennln.testaquariux.dtos.responses.WalletAssetResponse;
import com.tiennln.testaquariux.services.UsersService;
import com.tiennln.testaquariux.services.Web3Service;
import com.tiennln.testaquariux.utils.Web3Util;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Web3 service.
 *
 * @author TienNLN on 31/03/2023
 */
@Service
@AllArgsConstructor
public class Web3ServiceImpl implements Web3Service {

    private final Web3j web3j;

    private UsersService usersService;

    @SneakyThrows
    @Override
    public List<WalletAssetResponse> getWalletBalance(String walletAddress) {

        var privateKey = usersService.getPrivateKeyByAddress(walletAddress);

        var balanceResponse = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST)
                .send();

        var ethBalance = balanceResponse.getBalance();
        var ethAssetDTO = WalletAssetResponse.builder()
                .tokenName(CommonConstant.ETH_SYMBOL)
                .balance(Web3Util.getBalanceFromRaw(ethBalance, CommonConstant.ETH_DIVISOR))
                .build();

        List<WalletAssetResponse> assetsBalance = new ArrayList<>();

        assetsBalance.add(ethAssetDTO);

        // get assets balance by calling transaction
//        org.web3j.abi.datatypes.Function balanceOf = new Function("balanceOf",
//                List.of(new Address(walletAddress)),
//                List.of(new TypeReference<Uint256>() {
//                }));
//        //call function from webj instance
//
//        EthCall response = web3j.ethCall(
//                        Transaction.createEthCallTransaction(
//                                walletAddress,
//                                TokenContract.USDT_CONTRACT,
//                                FunctionEncoder.encode(balanceOf)),
//                        DefaultBlockParameterName.LATEST)
//                .send();
//
//        List<Type> types = FunctionReturnDecoder.decode(response.getValue(), balanceOf.getOutputParameters());
//        BigInteger usdtBalance = (BigInteger) types.get(0).getValue();
//        System.out.println(usdtBalance);
//        var usdtAssetDTO = WalletAssetResponse.builder()
//                .tokenName("USDT")
//                .balance(Web3Util.getBalanceFromRaw(usdtBalance, 1000000L))
//                .build();

        var testBalance = Web3Util.getUSDTContract(privateKey);

        var usdtBalance = testBalance.balanceOf(walletAddress).send();

        var usdtAssetDTO = WalletAssetResponse.builder()
                .tokenName(CommonConstant.USDT_SYMBOL)
                .balance(Web3Util.getBalanceFromRaw(usdtBalance, CommonConstant.USDT_DIVISOR))
                .build();

        assetsBalance.add(usdtAssetDTO);

        return assetsBalance;
    }
}

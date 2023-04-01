package com.tiennln.testaquariux.services.impl;

import com.tiennln.testaquariux.constants.CommonConstant;
import com.tiennln.testaquariux.constants.TokenContract;
import com.tiennln.testaquariux.dtos.responses.WalletAssetResponse;
import com.tiennln.testaquariux.services.Web3Service;
import com.tiennln.testaquariux.utils.Web3Util;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;
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

    private final Web3j web3j = Web3j.build(new HttpService(CommonConstant.ETH_RPC));

    @SneakyThrows
    @Override
    public List<WalletAssetResponse> getWalletBalance(String walletAddress) {

        var balanceResponse = web3j.ethGetBalance(walletAddress, DefaultBlockParameterName.LATEST)
                .send();

        var ethBalance = balanceResponse.getBalance();
        var ethAssetDTO = WalletAssetResponse.builder()
                .tokenName("ETH")
                .balance(new BigDecimal(ethBalance).divide(new BigDecimal(CommonConstant.DIVIDED_NUMBER)))
                .build();

        List<WalletAssetResponse> assetsBalance = new ArrayList<>();

        assetsBalance.add(ethAssetDTO);

        org.web3j.abi.datatypes.Function balanceOf = new Function("balanceOf",
                List.of(new Address(walletAddress)),
                List.of(new TypeReference<Uint256>() {
                }));
        //call function from webj instance

        EthCall response = web3j.ethCall(
                        Transaction.createEthCallTransaction(
                                walletAddress,
                                TokenContract.USDT_CONTRACT,
                                FunctionEncoder.encode(balanceOf)),
                        DefaultBlockParameterName.LATEST)
                .send();

        List<Type> types = FunctionReturnDecoder.decode(response.getValue(), balanceOf.getOutputParameters());
        BigInteger usdtBalance = (BigInteger) types.get(0).getValue();
        var usdtAssetDTO = WalletAssetResponse.builder()
                .tokenName("USDT")
                .balance(Web3Util.getBalanceFromRaw(usdtBalance, 1000000L))
                .build();

        assetsBalance.add(usdtAssetDTO);

        return assetsBalance;
    }
}

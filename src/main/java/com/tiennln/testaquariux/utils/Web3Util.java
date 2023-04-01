package com.tiennln.testaquariux.utils;

import com.tiennln.testaquariux.TransactionType;
import com.tiennln.testaquariux.constants.CommonConstant;
import com.tiennln.testaquariux.constants.TokenContract;
import com.tiennln.testaquariux.contracts.USDTContract;
import com.tiennln.testaquariux.dtos.responses.TransactionResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Ethereum;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author TienNLN on 01/04/2023
 */
@Component
@AllArgsConstructor
@Slf4j
public class Web3Util {
    private static Web3j web3j;

    @Autowired
    private void setWeb3j(Web3j web3jDI) {
        web3j = web3jDI;
    }

    public static BigDecimal getBalanceFromRaw(BigInteger rawBalance, long divisor) {
        return new BigDecimal(rawBalance).divide(new BigDecimal(divisor));
    }

    public static USDTContract getUSDTContract(String walletPrivateKey) {

        Credentials credentials = CredentialUtil.getCredentialFromPrivateKey(walletPrivateKey);

        FastRawTransactionManager txManager = getFastRawTransactionManagerByPrivateKey(credentials, walletPrivateKey);

        return USDTContract.load(TokenContract.USDT_CONTRACT,
                web3j,
                txManager,
                new DefaultGasProvider());
    }

    @SneakyThrows
    public static List<TransactionResponse> getAllTransaction(String address) {

        var block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true)
                .send()
                .getBlock();
        block.getTimestamp();

        var transactions = block.getTransactions();

        return transactions
                .stream()
//                .filter(transactionResult -> {
//                    EthBlock.TransactionObject txObj = (EthBlock.TransactionObject) transactionResult.get();
//
//                    return (txObj.getFrom().equalsIgnoreCase(address)
//                            || txObj.getTo().equalsIgnoreCase(address));
//                })
                .map(transactionResult -> {
                    EthBlock.TransactionObject txObj = (EthBlock.TransactionObject) transactionResult.get();

                    EthGetTransactionReceipt txReceipt = null;
                    try {
                        txReceipt = web3j.ethGetTransactionReceipt(txObj.getHash()).send();
                    } catch (IOException e) {
                        log.error("error when trying to get txReceipt");
                    }

                    txReceipt.getResult().getLogs();

                    String token = null;

                    if (!txReceipt.getResult().getLogs().isEmpty()) {
                        var contractAddress = txReceipt.getResult().getLogs().get(0).getAddress();

                        if (contractAddress.equalsIgnoreCase(TokenContract.USDT_CONTRACT))
                            token = CommonConstant.USDT_SYMBOL;
                        else
                            token = contractAddress;
                    }

                    return TransactionResponse.builder()
                            .value(new BigDecimal(txObj.get().getValue()))
                            .transactionType(TransactionType.TRANSFER)
                            .fromAddress(txObj.get().getFrom())
                            .toAddress(txObj.get().getTo())
                            .token(token)
                            .timestamp(new BigDecimal(block.getTimestamp()))
                            .build();
                })
                .toList();
    }


    public static FastRawTransactionManager getFastRawTransactionManagerByPrivateKey(Credentials credentials, String privateKey) {
        return new FastRawTransactionManager(web3j, credentials, 1);
    }
}

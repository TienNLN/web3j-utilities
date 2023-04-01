package com.tiennln.testaquariux.utils;

import com.tiennln.testaquariux.constants.TokenContract;
import com.tiennln.testaquariux.contracts.USDTContract;
import lombok.AllArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author TienNLN on 01/04/2023
 */
@Component
@AllArgsConstructor
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

    public static FastRawTransactionManager getFastRawTransactionManagerByPrivateKey(Credentials credentials, String privateKey) {
        return new FastRawTransactionManager(web3j, credentials, 1);
    }
}

package com.tiennln.testaquariux.utils;

import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author TienNLN on 01/04/2023
 */
@Slf4j
public class CredentialUtil {

    public static Credentials getCredentialFromPrivateKey(String privateKey) {
        try {

            BigInteger key = new BigInteger(privateKey, 16);
            ECKeyPair ecKeyPair = ECKeyPair.create(key.toByteArray());

            return Credentials.create(ecKeyPair);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Invalid privateKey: " + privateKey);
        }
        return null;
    }

    public static String decodePrivateKey(String encodedPrivateKey) {
        return new String(Base64.getDecoder().decode(encodedPrivateKey), StandardCharsets.UTF_8);
    }
}

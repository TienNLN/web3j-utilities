package com.tiennln.testaquariux.utils;

import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * The type Credential util.
 *
 * @author TienNLN on 01/04/2023
 */
@Slf4j
public class CredentialUtil {

    /**
     * Gets credential from private key.
     *
     * @param privateKey the private key
     * @return the credential from private key
     */
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

    /**
     * Decode private key string.
     *
     * @param encodedPrivateKey the encoded private key
     * @return the string
     */
    public static String decodePrivateKey(String encodedPrivateKey) {
        return new String(Base64.getDecoder().decode(encodedPrivateKey), StandardCharsets.UTF_8);
    }
}

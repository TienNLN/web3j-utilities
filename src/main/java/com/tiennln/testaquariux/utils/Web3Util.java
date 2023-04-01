package com.tiennln.testaquariux.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author TienNLN on 01/04/2023
 */
public class Web3Util {

    public static BigDecimal getBalanceFromRaw(BigInteger rawBalance, long divisor) {
        return new BigDecimal(rawBalance).divide(new BigDecimal(divisor));
    }
}

package com.tiennln.testaquariux.services;

import com.tiennln.testaquariux.dtos.payloads.BuyPayload;
import com.tiennln.testaquariux.dtos.payloads.SellPayload;
import com.tiennln.testaquariux.exceptions.BaseException;

/**
 * The interface Transaction service.
 *
 * @author TienNLN on 02/04/2023
 */
public interface TransactionService {

    /**
     * Buy token.
     *
     * @param payload the payload
     * @throws BaseException the base exception
     */
    void buyToken(BuyPayload payload) throws BaseException;

    /**
     * Sell token.
     *
     * @param payload the payload
     * @throws BaseException the base exception
     */
    void sellToken(SellPayload payload) throws BaseException;
}

package com.tiennln.testaquariux.services;

import com.tiennln.testaquariux.dtos.responses.TransactionResponse;

import java.util.List;

/**
 * The interface Transaction history service.
 *
 * @author TienNLN on 02/04/2023
 */
public interface TransactionHistoryService {

    /**
     * Gets transaction history.
     *
     * @param walletAddress the wallet address
     * @return the transaction history
     */
    List<TransactionResponse> getTransactionHistory(String walletAddress);
}

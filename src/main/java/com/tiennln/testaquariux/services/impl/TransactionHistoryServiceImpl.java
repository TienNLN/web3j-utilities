package com.tiennln.testaquariux.services.impl;

import com.tiennln.testaquariux.dtos.responses.TransactionResponse;
import com.tiennln.testaquariux.repositories.TransactionHistoryRepository;
import com.tiennln.testaquariux.repositories.UserRepository;
import com.tiennln.testaquariux.services.TransactionHistoryService;
import com.tiennln.testaquariux.services.Web3Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Transaction history service.
 *
 * @author TienNLN on 02/04/2023
 */
@Service
@AllArgsConstructor
@Slf4j
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    private UserRepository userRepository;

    private Web3Service web3Service;
    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public List<TransactionResponse> getTransactionHistory(String walletAddress) {
        var user = userRepository.findUserByWalletAddress(walletAddress);

        var transactionHistories = transactionHistoryRepository.findAllByUser(user);

        var web3Transactions = new ArrayList<TransactionResponse>();

        try {
            web3Service.getTransactions(walletAddress);
        } catch (Exception e) {
            log.error("Error getting transactions from Blockchain");
        }

        var transactionResponse = new java.util.ArrayList<>(transactionHistories.stream().map(transactionHistory -> TransactionResponse.builder()
                        .transactionType(transactionHistory.getAction())
                        .tradingPair(transactionHistory.getTradingPair())
                        .value(transactionHistory.getTransactionValue().movePointLeft(5))
                        .createdDate(transactionHistory.getCreatedDate())
                        .build())
                .toList());

        if (!web3Transactions.isEmpty()) {
            transactionResponse.addAll(web3Transactions);
        }

        return transactionResponse;
    }
}

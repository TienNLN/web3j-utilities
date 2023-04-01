package com.tiennln.testaquariux.controllers;

import com.tiennln.testaquariux.dtos.payloads.BuyPayload;
import com.tiennln.testaquariux.dtos.payloads.SellPayload;
import com.tiennln.testaquariux.dtos.responses.ResponseDTO;
import com.tiennln.testaquariux.exceptions.BaseException;
import com.tiennln.testaquariux.services.TransactionHistoryService;
import com.tiennln.testaquariux.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Transaction controller.
 *
 * @author TienNLN on 02/04/2023
 */
@RestController
@AllArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    private TransactionHistoryService transactionHistoryService;


    /**
     * Buy token response entity.
     *
     * @param buyPayload the buy payload
     * @return the response entity
     * @throws BaseException the base exception
     */
    @PostMapping("/buy")
    public ResponseEntity<?> buyToken(@RequestBody BuyPayload buyPayload) throws BaseException {
        transactionService.buyToken(buyPayload);
        return ResponseEntity.ok(ResponseDTO.builder().message("Success").build());
    }

    /**
     * Sell token response entity.
     *
     * @param sellPayload the sell payload
     * @return the response entity
     * @throws BaseException the base exception
     */
    @PostMapping("/sell")
    public ResponseEntity<?> sellToken(@RequestBody SellPayload sellPayload) throws BaseException {
        transactionService.sellToken(sellPayload);
        return ResponseEntity.ok(ResponseDTO.builder().message("Success").build());
    }

    /**
     * Gets transactions.
     *
     * @param address the address
     * @return the transactions
     */
    @GetMapping("/{address}")
    public ResponseEntity<?> getTransactions(@PathVariable String address) {
        return new ResponseEntity<>(transactionHistoryService.getTransactionHistory(address), HttpStatus.OK);
    }
}

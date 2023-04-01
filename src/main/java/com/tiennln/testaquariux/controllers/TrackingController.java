package com.tiennln.testaquariux.controllers;

import com.tiennln.testaquariux.dtos.responses.ResponseDTO;
import com.tiennln.testaquariux.services.BookTickerService;
import com.tiennln.testaquariux.services.Web3Service;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author TienNLN on 31/03/2023
 */
@RestController
@RequestMapping(value = "/api/tracking")
@AllArgsConstructor
public class TrackingController {

    private BookTickerService bookTickerService;

    private Web3Service web3Service;

    @GetMapping(value = "/latest-best-aggregate")
    public ResponseEntity<?> getLatestBestAggregate(@RequestParam String tradingPair) {
        var latestBestAggregate = bookTickerService.getLatestBestAggregatePrice(tradingPair);

        return new ResponseEntity<>(ResponseDTO.builder()
                .message("Finish")
                .result(latestBestAggregate)
                .statusCode(200L)
                .build(),
                HttpStatus.OK);
    }

    @GetMapping("/balance/{address}")
    public ResponseEntity<?> ethBalance(@PathVariable String address) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .result(web3Service.getWalletBalance(address))
                .build(), HttpStatus.OK);
    }
}

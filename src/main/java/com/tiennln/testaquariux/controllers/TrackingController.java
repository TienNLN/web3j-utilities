package com.tiennln.testaquariux.controllers;

import com.tiennln.testaquariux.dtos.responses.ResponseDTO;
import com.tiennln.testaquariux.services.BookTickerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TienNLN on 31/03/2023
 */
@RestController
@RequestMapping(value = "/api/tracking")
@AllArgsConstructor
public class TrackingController {

    private BookTickerService bookTickerService;

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
}

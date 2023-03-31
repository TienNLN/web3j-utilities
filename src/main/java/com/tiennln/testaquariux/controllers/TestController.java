package com.tiennln.testaquariux.controllers;

import com.tiennln.testaquariux.clients.BinanceClient;
import com.tiennln.testaquariux.services.BinanceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TienNLN on 31/03/2023
 */
@RestController
public class TestController {

    @Autowired
    private BinanceService binanceService;

    @GetMapping("/bookTicker")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(binanceService.getBookTicker());
    }
}

package com.tiennln.testaquariux.clients;

import com.tiennln.testaquariux.dtos.responses.binance.BinanceBookTickerResponse;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author TienNLN on 31/03/2023
 */
@FeignClient(name = "binance-client", url = "https://api.binance.com")
public interface BinanceClient {

    @GetMapping(value = "/api/v3/ticker/bookTicker", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getBookTicker();
}

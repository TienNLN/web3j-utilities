package com.tiennln.testaquariux.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The interface Binance client.
 *
 * @author TienNLN on 31/03/2023
 */
@FeignClient(name = "binance-client", url = "https://api.binance.com")
public interface BinanceClient {

    /**
     * Gets book ticker.
     *
     * @return the book ticker
     */
    @GetMapping(value = "/api/v3/ticker/bookTicker", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> getBookTicker();
}

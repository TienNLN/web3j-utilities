package com.tiennln.testaquariux.services;

import com.tiennln.testaquariux.dtos.responses.binance.BinanceBookTickerResponse;

import java.util.List;

/**
 * The interface Binance service.
 *
 * @author TienNLN on 31/03/2023
 */
public interface BinanceService {

    /**
     * Gets book ticker.
     *
     * @return the book ticker
     */
    List<BinanceBookTickerResponse> getBookTicker();
}

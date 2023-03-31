package com.tiennln.testaquariux.services;

import com.tiennln.testaquariux.dtos.responses.binance.BinanceBookTickerResponse;

import java.util.List;

/**
 * @author TienNLN on 31/03/2023
 */
public interface BookTickerService {

    void insert(List<BinanceBookTickerResponse> bookTickers);
}

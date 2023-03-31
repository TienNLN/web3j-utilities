package com.tiennln.testaquariux.services;

import com.tiennln.testaquariux.dtos.responses.BestAggregatePriceResponse;
import com.tiennln.testaquariux.dtos.responses.binance.BinanceBookTickerResponse;

import java.util.List;

/**
 * The interface Book ticker service.
 *
 * @author TienNLN on 31/03/2023
 */
public interface BookTickerService {

    /**
     * Insert.
     *
     * @param bookTickers the book tickers
     */
    void insert(List<BinanceBookTickerResponse> bookTickers);

    /**
     * Gets latest best aggregate price.
     *
     * @param tradingPair the trading pair
     * @return the latest best aggregate price
     */
    BestAggregatePriceResponse getLatestBestAggregatePrice(String tradingPair);
}

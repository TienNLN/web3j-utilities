package com.tiennln.testaquariux.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiennln.testaquariux.dtos.responses.BestAggregatePriceResponse;
import com.tiennln.testaquariux.dtos.responses.binance.BinanceBookTickerResponse;
import com.tiennln.testaquariux.entities.BookTicker;
import com.tiennln.testaquariux.repositories.BookTickerRepository;
import com.tiennln.testaquariux.services.BookTickerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Book ticker service.
 *
 * @author TienNLN on 31/03/2023
 */
@Service
@AllArgsConstructor
public class BookTickerServiceImpl implements BookTickerService {

    private BookTickerRepository bookTickerRepository;

    private ObjectMapper objectMapper;

    @Override
    public void insert(List<BinanceBookTickerResponse> bookTickers) {

        var targetBookTickers = bookTickers.parallelStream().map(binanceInput -> BookTicker.builder()
                        .askPrice(binanceInput.getAskPrice())
                        .askQuantity(binanceInput.getAskQty())
                        .bidPrice(binanceInput.getBidPrice())
                        .bidQuantity(binanceInput.getBidQty())
                        .tradingPair(binanceInput.getSymbol())
                        .build())
                .toList();

        targetBookTickers.parallelStream().forEach(bookTicker -> {
            var oldBookTicker = bookTickerRepository.findById(bookTicker.getTradingPair());

            if (!oldBookTicker.isEmpty()) {
                oldBookTicker.ifPresent(tempBookTicker -> {
                    if (tempBookTicker.getAskPrice().compareTo(bookTicker.getAskPrice()) == -1) {
                        bookTicker.setAskPrice(tempBookTicker.getAskPrice());
                    }

                    if (tempBookTicker.getBidPrice().compareTo(bookTicker.getBidPrice()) == 1) {
                        bookTicker.setBidPrice(tempBookTicker.getBidPrice());
                    }
                });
            }
        });

        targetBookTickers.parallelStream().forEach(bookTickerRepository::save);
    }

    @Override
    public BestAggregatePriceResponse getLatestBestAggregatePrice(String tradingPair) {
        var bookTicker = bookTickerRepository.findById(tradingPair);

        return bookTicker.map(ticker -> BestAggregatePriceResponse.builder()
                        .tradingPair(tradingPair)
                        .bestBuyPrice(ticker.getAskPrice())
                        .bestSellPrice(ticker.getBidPrice())
                        .build())
                .orElse(null);
    }
}

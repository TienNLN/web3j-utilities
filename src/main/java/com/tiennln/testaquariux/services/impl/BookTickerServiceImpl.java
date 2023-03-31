package com.tiennln.testaquariux.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiennln.testaquariux.dtos.responses.binance.BinanceBookTickerResponse;
import com.tiennln.testaquariux.entities.BookTicker;
import com.tiennln.testaquariux.repositories.BookTickerRepository;
import com.tiennln.testaquariux.services.BookTickerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
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

        targetBookTickers.parallelStream().forEach(bookTickerRepository::save);

        System.out.println("result: " + bookTickerRepository.findAll());
    }
}

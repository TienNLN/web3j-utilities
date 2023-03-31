package com.tiennln.testaquariux.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiennln.testaquariux.clients.BinanceClient;
import com.tiennln.testaquariux.dtos.responses.binance.BinanceBookTickerResponse;
import com.tiennln.testaquariux.services.BinanceService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TienNLN on 31/03/2023
 */
@Service
@AllArgsConstructor
public class BinanceServiceImpl implements BinanceService {

    private BinanceClient binanceClient;

    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public List<BinanceBookTickerResponse> getBookTicker() {
        String bookTickerResponseString = binanceClient.getBookTicker().getBody();

        return objectMapper.readValue(bookTickerResponseString, new TypeReference<>() {});
    }
}

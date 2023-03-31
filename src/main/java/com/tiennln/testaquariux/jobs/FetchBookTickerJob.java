package com.tiennln.testaquariux.jobs;

import com.tiennln.testaquariux.services.BinanceService;
import com.tiennln.testaquariux.services.BookTickerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author TienNLN on 31/03/2023
 */
@Slf4j
@Component
@AllArgsConstructor
public class FetchBookTickerJob {

    private BinanceService binanceService;

    private BookTickerService bookTickerService;

    @Scheduled(fixedRate = 10000)
    public void fetchBookTicker() {
        log.info("start fetch book ticker at " + new Date().getTime());

        var bookTickers = binanceService.getBookTicker();

        bookTickerService.insert(bookTickers);

        log.info("finish fetch book ticker at " + new Date().getTime());
    }
}

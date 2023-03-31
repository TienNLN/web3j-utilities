package com.tiennln.testaquariux.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author TienNLN on 31/03/2023
 */
@Entity
@Table(name = "BOOK_TICKER")
public class BookTicker implements Serializable {
    @Serial
    private static final long serialVersionUID = 8141622182918809762L;

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "TRADING_PAIR")
    private String tradingPair;

    @Column(name = "BID_PRICE")
    private Long bidPrice;

    @Column(name = "BID_QUANTITY")
    private Integer bidQuantity;

    @Column(name = "ASK_PRICE")
    private Long askPrice;

    @Column(name = "ASK_QUANTITY")
    private Integer askQuantity;
}

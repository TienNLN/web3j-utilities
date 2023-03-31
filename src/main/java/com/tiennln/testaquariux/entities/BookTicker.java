package com.tiennln.testaquariux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author TienNLN on 31/03/2023
 */
@Entity
@Table(name = "BOOK_TICKER", schema = "PUBLIC")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTicker implements Serializable {
    @Serial
    private static final long serialVersionUID = 8141622182918809762L;

    @Id
    @Column(name = "TRADING_PAIR", unique = true)
    private String tradingPair;

    @Column(name = "BID_PRICE")
    private BigDecimal bidPrice;

    @Column(name = "BID_QUANTITY")
    private BigDecimal bidQuantity;

    @Column(name = "ASK_PRICE")
    private BigDecimal askPrice;

    @Column(name = "ASK_QUANTITY")
    private BigDecimal askQuantity;
}

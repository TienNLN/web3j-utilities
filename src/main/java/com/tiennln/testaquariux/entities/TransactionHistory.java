package com.tiennln.testaquariux.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author TienNLN on 02/04/2023
 */
@Entity
@Table(name = "WALLET_TRANSACTIONS", schema = "PUBLIC")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory implements Serializable {
    @Serial
    private static final long serialVersionUID = -6944278675502560522L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "TRADING_PAIR")
    private String tradingPair;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "TRANSACTION_VALUE")
    private BigDecimal transactionValue;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}

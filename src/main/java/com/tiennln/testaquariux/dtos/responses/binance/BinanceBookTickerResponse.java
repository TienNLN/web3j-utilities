package com.tiennln.testaquariux.dtos.responses.binance;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author TienNLN on 31/03/2023
 */
@Data
public class BinanceBookTickerResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -6368433648633871695L;

    private String symbol;
    private BigDecimal bidPrice;
    private BigDecimal bidQty;
    private BigDecimal askPrice;
    private BigDecimal askQty;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

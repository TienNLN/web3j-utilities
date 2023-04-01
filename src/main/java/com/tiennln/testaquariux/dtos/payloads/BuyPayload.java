package com.tiennln.testaquariux.dtos.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author TienNLN on 02/04/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyPayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 1742510735481896781L;

    private String tradingPair;
    private BigDecimal value;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

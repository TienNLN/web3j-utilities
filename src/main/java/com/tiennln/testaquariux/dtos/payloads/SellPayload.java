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
 * The type Sell payload.
 *
 * @author TienNLN on 02/04/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellPayload implements Serializable {
    @Serial
    private static final long serialVersionUID = 2672370024910322160L;

    private String walletAddress;
    private String privateKey;
    private String tradingPair;
    private BigDecimal value;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

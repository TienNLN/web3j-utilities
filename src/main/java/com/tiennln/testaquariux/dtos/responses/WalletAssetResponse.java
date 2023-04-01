package com.tiennln.testaquariux.dtos.responses;

import lombok.Builder;
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
@Builder
public class WalletAssetResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 2894060191456099371L;

    private String tokenName;
    private BigDecimal balance;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

package com.tiennln.testaquariux.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The type Transaction response.
 *
 * @author TienNLN on 01/04/2023
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -159822073318742677L;

//    @JsonIgnore
    private BigDecimal value;
    private BigDecimal timestamp;
    private String fromAddress;
    private String toAddress;
    private String token;
    private String transactionType;
    private String tradingPair;
    private Date createdDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}

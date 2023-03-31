package com.tiennln.testaquariux.dtos.responses;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.io.Serializable;

/**
 * The type Response dto.
 */
@Data
@Builder
public class ResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5645697495366848624L;
    private Long statusCode;
    private String message;
    private Object result;

    /**
     * Instantiates a new Response dto.
     *
     * @param statusCode the status code
     * @param message    the message
     */
    public ResponseDTO(Long statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    /**
     * Instantiates a new Response dto.
     *
     * @param statusCode the status code
     * @param message    the message
     * @param result     the result
     */
    public ResponseDTO(Long statusCode, String message, Object result) {
        this.statusCode = statusCode;
        this.message = message;
        this.result = result;
    }

    /**
     * Success response dto.
     *
     * @return the response dto
     */
    public static ResponseDTO success() {
        return new ResponseDTO(200L, "Success");
    }


    /**
     * Internal service error response dto.
     *
     * @return the response dto
     */
    public static ResponseDTO internalServerError() {
        return new ResponseDTO(500L, "Internal server error");
    }

    /**
     * Time out response dto.
     *
     * @return the response dto
     */
    public static ResponseDTO timeOut() {
        return new ResponseDTO(408L, "Request Time Out");
    }

    /**
     * Bad request response dto.
     *
     * @return the response dto
     */
    public static ResponseDTO badRequest() {
        return new ResponseDTO(400L, "Bad request");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}

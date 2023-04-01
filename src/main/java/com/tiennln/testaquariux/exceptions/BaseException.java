package com.tiennln.testaquariux.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * The type Base exception.
 *
 * @author TienNLN on 02/04/2023
 */
@Getter
@Setter
public class BaseException extends Exception {

    @Serial
    private static final long serialVersionUID = -2382067086165213602L;
    /**
     * The Status.
     */
    protected final Long statusCode;
    /**
     * The Message.
     */
    protected final String message;
    /**
     * The Http status.
     */
    protected final HttpStatus httpStatus;

    /**
     * The Args.
     */
    protected final Object[] args;

    /**
     * Instantiates a new Base exception.
     *
     * @param statusCode the status code
     * @param message    the message
     * @param httpStatus the http status
     */
    public BaseException(Long statusCode, String message, HttpStatus httpStatus) {
        this.args = new Object[0];
        this.statusCode = statusCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Instantiates a new Base exception.
     *
     * @param statusCode the status code
     * @param message    the message
     * @param httpStatus the http status
     * @param args       the args
     */
    protected BaseException(Long statusCode, String message, HttpStatus httpStatus, Object... args) {
        this.args = args;
        this.statusCode = statusCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

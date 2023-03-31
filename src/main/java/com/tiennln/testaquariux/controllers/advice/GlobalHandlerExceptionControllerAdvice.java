package com.tiennln.testaquariux.controllers.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiennln.testaquariux.dtos.responses.ResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author TienNLN on 31/03/2023
 */
@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalHandlerExceptionControllerAdvice {
    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;


    /**
     * Global handler exception - handle all throwable that can be caused in project when you are missing try-catch exception.
     * This is a backup handle exception, I don't recommend to let this happened so please handle exceptions in your function as much as you can.
     *
     * @param request   the request
     * @param throwable the throwable
     * @return the response entity
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<?> handleException(HttpServletRequest request, Throwable throwable) {
        log.error(throwable.getMessage());
        return new ResponseEntity<>(ResponseDTO.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

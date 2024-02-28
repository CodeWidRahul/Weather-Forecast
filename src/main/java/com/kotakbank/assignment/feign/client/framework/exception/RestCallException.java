package com.kotakbank.assignment.feign.client.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class RestCallException extends RuntimeException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final String errorResponseBody;
}

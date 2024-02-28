package com.kotakbank.assignment.feign.client.framework.exception;

public class NoValuePresentException extends RuntimeException {
    public NoValuePresentException(String message) {
        super(message);
    }

    public NoValuePresentException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NoValuePresentException(Throwable throwable) {
        super(throwable);
    }

    public NoValuePresentException(String message, Throwable throwable, boolean enableSuppression, boolean writtableStackTrace) {
        super(message, throwable, enableSuppression, writtableStackTrace);
    }
}

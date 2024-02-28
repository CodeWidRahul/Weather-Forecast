package com.kotakbank.assignment.feign.client.framework.exception;

public class NoSuchHttpMethodExistsException extends RuntimeException {
    public NoSuchHttpMethodExistsException(String message) {
        super(message);
    }

    public NoSuchHttpMethodExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NoSuchHttpMethodExistsException(Throwable throwable) {
        super(throwable);
    }

    public NoSuchHttpMethodExistsException(String message, Throwable throwable, boolean enableSuppression, boolean writtableStackTrace) {
        super(message, throwable, enableSuppression, writtableStackTrace);
    }
}

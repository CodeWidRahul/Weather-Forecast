package com.kotakbank.assignment.feign.client.framework.builder;

import com.kotakbank.assignment.feign.client.framework.status.ApplicationStatus;

public class MessageBuilder {

    public static String buildMessage(ApplicationStatus status) {
        return status.getErrorCode() + ":" + status.getErrorMessage();
    }
}

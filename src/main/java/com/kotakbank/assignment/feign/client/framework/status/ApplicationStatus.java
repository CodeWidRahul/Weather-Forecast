package com.kotakbank.assignment.feign.client.framework.status;

import com.kotakbank.assignment.feign.client.framework.status.code.FeignErrorCode;
import com.kotakbank.assignment.feign.client.framework.status.code.FeignErrorMessage;

public enum ApplicationStatus {
    INVALID_HTTP_METHOD(FeignErrorCode.INVALID_HTTP_METHOD, FeignErrorMessage.INVALID_HTTP_METHOD),
    INVALID_PATH_PARAMETER(FeignErrorCode.NO_VALUE_PRESENT_FOR_PATH_PARAM, FeignErrorMessage.NO_VALUE_PRESENT_FOR_PATH_PARAM),
    INVALID_QUERY_PARAMETER(FeignErrorCode.NO_VALUE_PRESENT_FOR_QUERY_PARAM, FeignErrorMessage.NO_VALUE_PRESENT_FOR_QUERY_PARAM);

    private final int errorCode;
    private final String errorMessage;

    ApplicationStatus(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

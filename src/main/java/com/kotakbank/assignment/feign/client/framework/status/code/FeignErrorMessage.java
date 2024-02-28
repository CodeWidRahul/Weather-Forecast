package com.kotakbank.assignment.feign.client.framework.status.code;

public final class FeignErrorMessage {
    public static final String INVALID_HTTP_METHOD = "No such http method exist for communication";
    public static final String NO_VALUE_PRESENT_FOR_PATH_PARAM = "Path parameter name or value is missing";
    public static final String NO_VALUE_PRESENT_FOR_QUERY_PARAM = "Query parameter name is missing";
    public static final String INVALID_RESPONSE = "The downstream system returns invalid response";
    public static final String INVALID_RETURN_VALUE = "The framework does not support the %s return value";
}
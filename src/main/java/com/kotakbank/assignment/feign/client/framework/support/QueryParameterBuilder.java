package com.kotakbank.assignment.feign.client.framework.support;

import com.kotakbank.assignment.feign.client.framework.annotation.HttpQueryParam;
import com.kotakbank.assignment.feign.client.framework.builder.MessageBuilder;
import com.kotakbank.assignment.feign.client.framework.exception.NoValuePresentException;
import com.kotakbank.assignment.feign.client.framework.status.ApplicationStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.lang.reflect.Parameter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class QueryParameterBuilder {
    public MultiValueMap<String, String> collectQueryParameters(Parameter[] parameters, Object[] args) {
        MultiValueMap<String, String> queryMap = new MultiValueMapAdapter<>(new HashMap<>());
        for (int index = 0; index < parameters.length; index++) {
            if (isMarkedWithQueryParam(parameters[index])) {
                Parameter parameter = parameters[index];
                String queryParamName = "";
                queryParamName = parameter.getAnnotation(HttpQueryParam.class).value();
                if (Objects.isNull(queryParamName) || Objects.equals(queryParamName, "")) {
                    queryParamName = parameter.getAnnotation(HttpQueryParam.class).name();
                    if (Objects.isNull(queryParamName) || Objects.equals(queryParamName, ""))
                        throw new NoValuePresentException(MessageBuilder.buildMessage(ApplicationStatus.INVALID_QUERY_PARAMETER));
                }
                queryMap.put(queryParamName, Collections.singletonList(URLEncoder.encode(String.valueOf(args[index]).trim(), StandardCharsets.UTF_8)));
            }
        }
        return queryMap;
    }

    private boolean isMarkedWithQueryParam(Parameter parameter) {
        return parameter.isAnnotationPresent(HttpQueryParam.class);
    }
}

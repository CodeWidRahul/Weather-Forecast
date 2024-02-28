package com.kotakbank.assignment.feign.client.framework.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CallDataModel {

    private String url;
    private HttpHeaders httpHeaders;
    private Optional<Object> requestBody;
    private HttpMethod methodToCall;
    private MultiValueMap<String, String> queryParams;
    private Map<String, Object> pathParams;
    private Type returnType;
}

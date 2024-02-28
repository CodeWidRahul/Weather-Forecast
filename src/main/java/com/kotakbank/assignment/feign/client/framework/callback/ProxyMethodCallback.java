package com.kotakbank.assignment.feign.client.framework.callback;

import com.kotakbank.assignment.feign.client.framework.annotation.HttpHeaderParam;
import com.kotakbank.assignment.feign.client.framework.annotation.HttpRequestBody;
import com.kotakbank.assignment.feign.client.framework.model.CallDataModel;
import com.kotakbank.assignment.feign.client.framework.support.*;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class ProxyMethodCallback implements MethodInterceptor, AnnotationDataAware {

    private final UrlResolver urlResolver;
    private final HttpRestMethodRecognizer httpRestMethodRecognizer;
    private final PathParamCollector pathParamCollector;
    private final QueryParameterBuilder queryParameterBuilder;
    private RestCallHandler restCalLHandler;
    private Map<String, Object> attributes;

    public ProxyMethodCallback(UrlResolver urlResolver, HttpRestMethodRecognizer httpRestMethodRecognizer,
                               PathParamCollector pathParamCollector, QueryParameterBuilder queryParameterBuilder,
                               RestCallHandler restCalLHandler) {
        this.urlResolver = urlResolver;
        this.httpRestMethodRecognizer = httpRestMethodRecognizer;
        this.pathParamCollector = pathParamCollector;
        this.queryParameterBuilder = queryParameterBuilder;
        this.restCalLHandler = restCalLHandler;
    }

    @Override
    public void setAnnotationData(Map<String, Object> annotationData) {
        this.attributes = annotationData;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String baseUrl = (String) getAttributeValue("baseUrl");
        baseUrl = urlResolver.resolveUrl(baseUrl);
        baseUrl = constructFinalUrl(baseUrl, method);
        HttpMethod methodToCall = httpRestMethodRecognizer.recognizeHttpMethodToCall(method);
        Map<String, Object> pathParameters = pathParamCollector.collectPathParameters(method.getParameters(), baseUrl, objects);
        MultiValueMap<String, String> queryParameters = queryParameterBuilder.collectQueryParameters(method.getParameters(), objects);
        HttpHeaders headers = processHeaderInformation(attributes, method, objects);
        Optional<Object> requestBody = processRequestBody(method, objects);
        Type returnType = method.getGenericReturnType();
        CallDataModel callDataModel = new CallDataModel(baseUrl, headers, requestBody, methodToCall, queryParameters, pathParameters, returnType);
        return restCalLHandler.handleRestCall(callDataModel);
    }

    private Optional<Object> processRequestBody(Method method, Object[] objects) {
        int parameterPosition = getParameterPositionMarkedWith(method.getParameters(), HttpRequestBody.class);
        if (parameterPosition != -1)
            return Optional.of(objects[parameterPosition]);
        else
            return Optional.empty();
    }

    private HttpHeaders processHeaderInformation(Map<String, Object> attributes, Method method, Object[] objects) {
        HttpHeaders headers = new HttpHeaders();
        AnnotationAttributes[] httpHeaders = (AnnotationAttributes[]) attributes.get("defaultHeaders");
        if (httpHeaders.length != 0) {
            processDefaultHeaderInformation(headers, httpHeaders);
            processHeaderFromParameters(headers, method, objects);
        } else
            processHeaderFromParameters(headers, method, objects);

        if (!headers.containsKey("Content-Type"))
            headers.setContentType(MediaType.APPLICATION_JSON);

        return headers;
    }

    private void processHeaderFromParameters(HttpHeaders headers, Method method, Object[] objects) {
        Parameter[] parameters = method.getParameters();
        int parameterIndex = getParameterPositionMarkedWith(parameters, HttpHeaderParam.class);
        if (parameterIndex != -1) {
            Object object = objects[parameterIndex];
            if (object instanceof HttpHeaders) {
                HttpHeaders httpHeaders = (HttpHeaders) object;
                httpHeaders.forEach(headers::addAll);
            } else if (object instanceof MultiValueMap) {
                MultiValueMap<String, String> headerMap = (MultiValueMap<String, String>) object;
                headerMap.forEach(headers::addAll);
            }
        }
    }

    private int getParameterPositionMarkedWith(Parameter[] parameters, Class<? extends Annotation> httpHeaderParamClass) {
        int parameterIndex = -1;
        boolean isParameterPresent = false;
        for (Parameter parameter : parameters) {
            parameterIndex++;
            if (parameter.isAnnotationPresent(httpHeaderParamClass)) {
                isParameterPresent = true;
                break;
            }
        }
        if (!isParameterPresent)
            parameterIndex = -1;
        return parameterIndex;
    }

    private void processDefaultHeaderInformation(HttpHeaders headers, AnnotationAttributes[] httpHeaders) {
        for (AnnotationAttributes httpHeader : httpHeaders) {
            String headerName = httpHeader.getString("name");
            String[] values = httpHeader.getStringArray("value");
            headers.addAll(headerName, Arrays.asList(values));
        }
    }

    private String constructFinalUrl(String baseUrl, Method method) {
        String subUrl = new SubUrlRecognizer().subUrl(method);
        if (subUrl.equals(""))
            return baseUrl;
        else if (subUrl.startsWith("/")) {
            if (subUrl.length() == 1)
                return baseUrl;
            else
                return baseUrl + subUrl;
        } else
            return baseUrl + "/" + subUrl;
    }

    private Object getAttributeValue(String baseUrl) {
        return attributes.get(baseUrl);
    }
}

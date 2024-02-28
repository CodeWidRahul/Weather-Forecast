package com.kotakbank.assignment.feign.client.framework.util;

import com.kotakbank.assignment.feign.client.framework.callback.ProxyMethodCallback;
import com.kotakbank.assignment.feign.client.framework.support.*;
import org.springframework.cglib.proxy.Enhancer;

import java.util.Map;

public class ProxyUtils implements AnnotationDataAware {
    private final UrlResolver urlResolver;
    private final HttpRestMethodRecognizer httpRestMethodRecognizer;
    private final QueryParameterBuilder queryParameterBuilder;
    private final PathParamCollector pathParamCollector;
    private final RestCallHandler restCallHandler;
    private Map<String, Object> attributes;

    public ProxyUtils(UrlResolver urlResolver, HttpRestMethodRecognizer httpRestMethodRecognizer,
                      QueryParameterBuilder queryParameterBuilder, PathParamCollector pathParamCollector,
                      RestCallHandler restCallHandler) {
        this.urlResolver = urlResolver;
        this.httpRestMethodRecognizer = httpRestMethodRecognizer;
        this.queryParameterBuilder = queryParameterBuilder;
        this.pathParamCollector = pathParamCollector;
        this.restCallHandler = restCallHandler;
    }

    @Override
    public void setAnnotationData(Map<String, Object> annotationData) {
        this.attributes = annotationData;
    }

    public Object createTargetObject(Class<?> type) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(new Class[]{type});
        ProxyMethodCallback callback = new ProxyMethodCallback(urlResolver, httpRestMethodRecognizer, pathParamCollector, queryParameterBuilder, restCallHandler);
        callback.setAnnotationData(attributes);
        enhancer.setCallback(callback);
        return enhancer.create();
    }
}

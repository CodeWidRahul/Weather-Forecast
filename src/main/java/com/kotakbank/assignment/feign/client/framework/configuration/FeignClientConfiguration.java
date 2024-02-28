package com.kotakbank.assignment.feign.client.framework.configuration;

import com.kotakbank.assignment.feign.client.framework.support.*;
import com.kotakbank.assignment.feign.client.framework.util.ProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FeignClientConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public PathParamCollector pathParamResolver() {
        return new PathParamCollector();
    }

    @Bean
    public QueryParameterBuilder queryParameterBuilder() {
        return new QueryParameterBuilder();
    }

    @Bean
    public HttpRestMethodRecognizer httpRestMethodRecognizer() {
        return new HttpRestMethodRecognizer();
    }

    @Bean
    public UrlResolver urlResolver() {
        return new UrlResolver(environment);
    }

    @Bean
    public ProxyUtils proxyUtils(UrlResolver urlResolver, HttpRestMethodRecognizer httpRestMethodRecognizer,
                                 QueryParameterBuilder queryParameterBuilder, PathParamCollector pathParamCollector,
                                 RestCallHandler restCallHandler) {
        return new ProxyUtils(urlResolver, httpRestMethodRecognizer, queryParameterBuilder, pathParamCollector, restCallHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public RestCallHandler restCallHandler(RestTemplate restTemplate) {
        return new RestCallHandler(restTemplate);
    }
}

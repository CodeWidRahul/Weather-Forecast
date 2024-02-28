package com.kotakbank.assignment.feign.client.framework.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface FeignClient {
    String baseUrl();

    HttpHeader[] defaultHeaders() default {};
}

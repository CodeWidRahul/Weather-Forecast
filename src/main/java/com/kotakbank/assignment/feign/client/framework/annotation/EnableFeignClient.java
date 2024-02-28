package com.kotakbank.assignment.feign.client.framework.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EnableFeignClient {
    String[] basePackages() default {};

    Class<?>[] basePackagesClasses() default {};
}

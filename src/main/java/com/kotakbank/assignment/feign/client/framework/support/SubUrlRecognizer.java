package com.kotakbank.assignment.feign.client.framework.support;

import com.kotakbank.assignment.feign.client.framework.annotation.*;

import java.lang.reflect.Method;

public class SubUrlRecognizer {

    public String subUrl(Method method) {
        if (method.isAnnotationPresent(HttpPost.class))
            return method.getAnnotation(HttpPost.class).value();
        else if (method.isAnnotationPresent(HttpGet.class))
            return method.getAnnotation(HttpGet.class).value();
        else if (method.isAnnotationPresent(HttpPut.class))
            return method.getAnnotation(HttpPut.class).value();
        else if (method.isAnnotationPresent(HttpDelete.class))
            return method.getAnnotation(HttpDelete.class).value();
        else if (method.isAnnotationPresent(HttpHead.class))
            return method.getAnnotation(HttpHead.class).value();
        else if (method.isAnnotationPresent(HttpOptions.class))
            return method.getAnnotation(HttpOptions.class).value();
        else if (method.isAnnotationPresent(HttpPatch.class))
            return method.getAnnotation(HttpPatch.class).value();
        else if (method.isAnnotationPresent(HttpTrace.class))
            return method.getAnnotation(HttpTrace.class).value();
        else
            return "";
    }
}

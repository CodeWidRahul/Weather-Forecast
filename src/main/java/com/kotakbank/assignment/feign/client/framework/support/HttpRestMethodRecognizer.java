package com.kotakbank.assignment.feign.client.framework.support;

import com.kotakbank.assignment.feign.client.framework.annotation.*;
import com.kotakbank.assignment.feign.client.framework.builder.MessageBuilder;
import com.kotakbank.assignment.feign.client.framework.exception.NoSuchHttpMethodExistsException;
import com.kotakbank.assignment.feign.client.framework.status.ApplicationStatus;
import org.springframework.http.HttpMethod;

import java.lang.reflect.Method;

public class HttpRestMethodRecognizer {

    public HttpMethod recognizeHttpMethodToCall(Method method) {
        if (method.isAnnotationPresent(HttpPost.class))
            return HttpMethod.POST;
        else if (method.isAnnotationPresent(HttpGet.class))
            return HttpMethod.GET;
        else if (method.isAnnotationPresent(HttpPut.class))
            return HttpMethod.PUT;
        else if (method.isAnnotationPresent(HttpDelete.class))
            return HttpMethod.DELETE;
        else if (method.isAnnotationPresent(HttpHead.class))
            return HttpMethod.HEAD;
        else if (method.isAnnotationPresent(HttpOptions.class))
            return HttpMethod.OPTIONS;
        else if (method.isAnnotationPresent(HttpPatch.class))
            return HttpMethod.PATCH;
        else if (method.isAnnotationPresent(HttpTrace.class))
            return HttpMethod.TRACE;
        else
            throw new NoSuchHttpMethodExistsException(MessageBuilder.buildMessage(ApplicationStatus.INVALID_HTTP_METHOD));
    }
}

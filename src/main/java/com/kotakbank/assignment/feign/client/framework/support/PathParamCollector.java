package com.kotakbank.assignment.feign.client.framework.support;

import com.kotakbank.assignment.feign.client.framework.annotation.HttpPathParam;

import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public class PathParamCollector {

    public Map<String, Object> collectPathParameters(Parameter[] parameters, String url, Object[] args) {
        Map<String, Object> pathParamMap = new HashMap<>();
        String[] pathComponent = url.split("/");
        List<String> pathParams = Arrays.asList(pathComponent).stream().filter(component -> component.startsWith("{") && component.endsWith("}"))
                .map(component -> component.substring(1, component.length() - 1)).collect(Collectors.toUnmodifiableList());
        for (String param : pathParams) {
            Optional<Parameter> parameterWithPathParam = Arrays.asList(parameters).stream()
                    .filter(parameter -> parameter.isAnnotationPresent(HttpPathParam.class))
                    .filter(parameter -> parameter.getAnnotation(HttpPathParam.class).name().equals(param))
                    .findFirst();
            if (parameterWithPathParam.isPresent()) {
                Parameter parameter = parameterWithPathParam.get();
                int position = findParameterPosition(parameters, parameter);
                if (position != -1)
                    pathParamMap.put(parameter.getAnnotation(HttpPathParam.class).name(), args[position]);
            }
        }
        return pathParamMap;
    }

    private int findParameterPosition(Parameter[] parameters, Parameter parameter) {
        int position = -1;
        boolean isPresent = false;
        for (Parameter param : parameters) {
            position++;
            if (param.getName().equals(parameter.getName()))
                isPresent = true;
        }
        if (!isPresent)
            position = -1;
        return position;
    }
}

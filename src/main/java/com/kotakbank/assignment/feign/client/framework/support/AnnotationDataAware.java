package com.kotakbank.assignment.feign.client.framework.support;

import java.util.Map;

public interface AnnotationDataAware {
    void setAnnotationData(Map<String, Object> annotationData);
}

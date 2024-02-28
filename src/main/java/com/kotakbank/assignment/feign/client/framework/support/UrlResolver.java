package com.kotakbank.assignment.feign.client.framework.support;

import org.springframework.core.env.Environment;

public class UrlResolver {

    private final Environment environment;

    public UrlResolver(Environment environment) {
        this.environment = environment;
    }

    public String resolveUrl(String urlExpression) {
        String url = "";
        if (urlExpression.startsWith("${") && urlExpression.endsWith("}")) {
            String key = urlExpression.trim().substring(2, urlExpression.length() - 1);
            url = environment.getProperty(key);
        } else {
            url = urlExpression;
        }
        return url;
    }
}

package com.kotakbank.assignment.feign.client.framework.factory;

import com.kotakbank.assignment.feign.client.framework.support.*;
import com.kotakbank.assignment.feign.client.framework.util.ProxyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class FeignClientFactoryBean implements FactoryBean<Object>, ApplicationContextAware, BeanFactoryAware, EnvironmentAware {
    private Environment environment;
    private Class<?> type;
    private Map<String, Object> attributes;
    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getObject() throws Exception {
        return getTarget();
    }

    private Object getTarget() {
        ProxyUtils proxyUtils = null;
        try {
            proxyUtils = getBean(ProxyUtils.class);
        } catch (NoSuchMethodException noSuchMethodException) {
            proxyUtils = new ProxyUtils(getUrlResolver(), getMethodRecognizer(), getQueryParameterBuilder()
                    , getPathParamCollector(), getRestCallHandler());
        }
        proxyUtils.setAnnotationData(attributes);
        return proxyUtils.createTargetObject(getObjectType());
    }

    private <T> T getBean(Class<T> clazz) throws NoSuchMethodException {
        try {
            return beanFactory != null ? beanFactory.getBean(clazz) : applicationContext.getBean(clazz);
        } catch (NoSuchBeanDefinitionException noSuchBeanDefinitionException) {
            return BeanUtils.instantiateClass(clazz.getDeclaredConstructor(UrlResolver.class,
                    HttpRestMethodRecognizer.class, QueryParameterBuilder.class, PathParamCollector.class,
                    RestCallHandler.class), getParameters());
        }
    }

    private Object[] getParameters() {
        UrlResolver urlResolver = getUrlResolver();
        HttpRestMethodRecognizer methodRecognizer = getMethodRecognizer();
        QueryParameterBuilder queryParameterBuilder = getQueryParameterBuilder();
        PathParamCollector pathParamCollector = getPathParamCollector();
        RestCallHandler restCallHandler = getRestCallHandler();
        return new Object[]{urlResolver, methodRecognizer, queryParameterBuilder, pathParamCollector, restCallHandler};
    }

    private RestCallHandler getRestCallHandler() {
        return new RestCallHandler(new RestTemplate());
    }

    private PathParamCollector getPathParamCollector() {
        return new PathParamCollector();
    }

    private QueryParameterBuilder getQueryParameterBuilder() {
        return new QueryParameterBuilder();
    }

    private HttpRestMethodRecognizer getMethodRecognizer() {
        return new HttpRestMethodRecognizer();
    }

    private UrlResolver getUrlResolver() {
        return new UrlResolver(environment);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}

package com.kotakbank.assignment.feign.client.framework.support;

import com.kotakbank.assignment.feign.client.framework.annotation.EnableFeignClient;
import com.kotakbank.assignment.feign.client.framework.annotation.FeignClient;
import com.kotakbank.assignment.feign.client.framework.factory.FeignClientFactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MultiValueMap;

import java.util.*;

public class FeignClientRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider classPathScanner = getClassPathScanner();
        classPathScanner.addIncludeFilter(new AnnotationTypeFilter(FeignClient.class));
        Set<String> basePackages = getBasePackages(importingClassMetadata);
        for (String basePackage : basePackages) {
            candidateComponents.addAll(classPathScanner.findCandidateComponents(basePackage));
        }

        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition) {
                AnnotatedBeanDefinition beanDefinition = (AnnotatedBeanDefinition) candidateComponent;
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(), "@FeignClient can only be applied on interface");
                MultiValueMap<String, Object> metaAttributes = annotationMetadata.getAllAnnotationAttributes(EnableFeignClient.class.getCanonicalName());
                String className = annotationMetadata.getClassName();
                registerFactoryBean(registry, className, metaAttributes);
            }
        }
    }

    private void registerFactoryBean(BeanDefinitionRegistry registry, String className, MultiValueMap<String, Object> metaAttributes) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactoryBean.class);
        beanDefinitionBuilder.addPropertyValue("type", className);
        beanDefinitionBuilder.addPropertyValue("attributes", metaAttributes);
        beanDefinitionBuilder.addPropertyValue("environment", environment);
        BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinitionBuilder.getBeanDefinition(), className);
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
    }

    private Set<String> getBasePackages(AnnotationMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableFeignClient.class.getCanonicalName());
        HashSet<String> basePackages = new HashSet<>();
        String[] packages = (String[]) attributes.get("basePackages");
        Collections.addAll(basePackages, packages);

        Class<?>[] basePackageClasses = (Class<?>[]) attributes.get("basePackageClasses");
        for (Class<?> clazz : basePackageClasses) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty())
            basePackages.add(ClassUtils.getPackageName(metadata.getClassName()));

        return basePackages;
    }

    private ClassPathScanningCandidateComponentProvider getClassPathScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, environment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation())
                        isCandidate = true;
                }
                return isCandidate;
            }
        };
    }
}

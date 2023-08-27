package com.study.microservices.employeeservice.integration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class SpringContextUtils {

    private final ApplicationContext applicationContext;

    public void printAllBeanDefinitions() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }

}

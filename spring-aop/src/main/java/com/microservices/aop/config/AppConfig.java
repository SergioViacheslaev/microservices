package com.microservices.aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.microservices.aop")
@EnableAspectJAutoProxy
public class AppConfig {

}

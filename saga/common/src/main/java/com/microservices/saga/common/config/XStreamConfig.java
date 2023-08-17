package com.microservices.saga.common.config;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XStreamConfig {

    @Bean
    XStream xStream() {
        XStream xStream = new XStream();

        xStream.allowTypesByWildcard(new String[]{"com.microservices.saga.**"});
        return xStream;
    }
}

package com.tiennln.testaquariux.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.web3j.protocol.Web3j;

import java.util.Collections;

/**
 * @author TienNLN on 31/03/2023
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduler.enabled", matchIfMissing = true)
@EnableAsync
@EnableFeignClients(basePackages = "com.tiennln.testaquariux.clients")
public class SpringConfig {

    @Bean
    public MappingJackson2HttpMessageConverter convertMessage() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        return converter;
    }
}

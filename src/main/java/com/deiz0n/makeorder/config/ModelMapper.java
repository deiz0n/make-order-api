package com.deiz0n.makeorder.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootConfiguration
public class ModelMapper {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}

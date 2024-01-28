package com.deiz0n.makeorder.api.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapper mapperRecord() {
        return mapper().registerModule(new RecordModule());
    }

}

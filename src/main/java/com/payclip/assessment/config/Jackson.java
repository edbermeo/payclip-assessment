package com.payclip.assessment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Configuration
public class Jackson {

    @Bean
    ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.setSerializationInclusion(NON_NULL);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
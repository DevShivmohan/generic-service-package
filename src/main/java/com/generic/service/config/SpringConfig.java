package com.generic.service.config;

import com.generic.service.service.IdGenerationStrategy;
import com.generic.service.service.impl.DefaultIdGenerationStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public IdGenerationStrategy idGeneratorStrategy(ObjectProvider<IdGenerationStrategy> strategies) {
        return strategies
                .stream()
                .findFirst()
                .orElseGet(DefaultIdGenerationStrategy::new);
    }
}

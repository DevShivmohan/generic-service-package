package com.generic.service.config;

import com.generic.service.service.IdGenerationStrategy;
import com.generic.service.service.impl.DefaultIdGenerationStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    @ConditionalOnMissingBean(IdGenerationStrategy.class)
    public IdGenerationStrategy idGenerationStrategy() {
        return new DefaultIdGenerationStrategy();
    }

}

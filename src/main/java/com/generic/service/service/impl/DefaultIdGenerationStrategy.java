package com.generic.service.service.impl;

import com.fasterxml.uuid.Generators;
import com.generic.service.service.IdGenerationStrategy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultIdGenerationStrategy implements IdGenerationStrategy {
    @Override
    public UUID generateId() {
        return Generators.timeBasedReorderedGenerator().generate();
    }
}

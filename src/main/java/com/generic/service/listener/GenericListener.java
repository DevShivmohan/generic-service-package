package com.generic.service.listener;

import com.generic.service.entity.GenericEntity;
import com.generic.service.service.GenericTimeCreator;
import com.generic.service.service.IdGenerationStrategy;
import com.generic.service.service.RequestContext;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local", "dev", "test", "staging", "prod", "qa", "uat"})
@AllArgsConstructor
public class GenericListener {
    private final IdGenerationStrategy idGenerationStrategy;
    private final GenericTimeCreator genericTimeCreator;
    private final RequestContext requestContext;

    @PrePersist
    protected void beforePersist(GenericEntity genericEntity) {
        genericEntity.setUuid(idGenerationStrategy.generateId());
        genericEntity.setCreatedAt(genericTimeCreator.createLocalDateTime());
        genericEntity.setUpdatedAt(genericTimeCreator.createLocalDateTime());
        genericEntity.setCreatedBy(requestContext.getUserFromRequestContextHolder().getUserId() == null ? null : requestContext.getUserFromRequestContextHolder().getUserId().toString());
        genericEntity.setUpdatedBy(requestContext.getUserFromRequestContextHolder().getUserId() == null ? null : requestContext.getUserFromRequestContextHolder().getUserId().toString());
    }

    @PreUpdate
    protected void beforeUpdate(GenericEntity genericEntity) {
        genericEntity.setUpdatedAt(genericTimeCreator.createLocalDateTime());
        genericEntity.setUpdatedBy(requestContext.getUserFromRequestContextHolder().getUserId() == null ? null : requestContext.getUserFromRequestContextHolder().getUserId().toString());
    }

}


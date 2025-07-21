package com.generic.service.listener;

import com.generic.service.entity.GenericEntity;
import com.generic.service.service.IdGenerationStrategy;
import com.generic.service.util.GenericTimeUtil;
import com.generic.service.util.RequestContext;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Profile({"local", "dev", "test", "staging", "prod", "qa", "uat"})
@AllArgsConstructor
public class GenericListener {
    private final IdGenerationStrategy idGenerationStrategy;

    @PrePersist
    protected void beforePersist(GenericEntity genericEntity) {
        genericEntity.setId(idGenerationStrategy.generateId());
        genericEntity.setCreatedAt(GenericTimeUtil.getLocalDateTimeInIST());
        genericEntity.setUpdatedAt(GenericTimeUtil.getLocalDateTimeInIST());
        genericEntity.setCreatedBy(Objects.requireNonNull(RequestContext.getUserFromRequestContextHolder()).getUserId());
        genericEntity.setUpdatedBy(RequestContext.getUserFromRequestContextHolder().getUserId());
        genericEntity.setTenantId(RequestContext.getUserFromRequestContextHolder().getTenantId());
    }

    @PreUpdate
    protected void beforeUpdate(GenericEntity genericEntity) {
        genericEntity.setUpdatedAt(GenericTimeUtil.getLocalDateTimeInIST());
        genericEntity.setUpdatedBy(Objects.requireNonNull(RequestContext.getUserFromRequestContextHolder()).getUserId());
    }

}


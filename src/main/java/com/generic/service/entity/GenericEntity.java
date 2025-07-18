package com.generic.service.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@MappedSuperclass
@EntityListeners(GenericEntity.class)
public abstract class GenericEntity implements Serializable {
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private Integer tenantId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private UUID updatedBy;

    @Column(updatable = false)
    private UUID createdBy;

    @JsonIgnore
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;
}

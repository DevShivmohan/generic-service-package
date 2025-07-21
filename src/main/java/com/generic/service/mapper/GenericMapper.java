package com.generic.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GenericMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    /**
     * Generic mapper
     *
     * @param sourceObject the source object
     * @param targetClass  the target class
     * @param <S>          source type
     * @param <T>          target type
     * @return mapped object
     */
    public static <S, T> T map(S sourceObject, Class<T> targetClass) {
        return sourceObject == null ? null : modelMapper.map(sourceObject, targetClass);
    }
}

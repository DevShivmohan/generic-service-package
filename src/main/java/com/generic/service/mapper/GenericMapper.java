package com.generic.service.mapper;

import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class GenericMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PUBLIC)
                .setMethodAccessLevel(AccessLevel.PUBLIC)
                .setSkipNullEnabled(true);
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

    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList == null ? null : sourceList.stream().map(element -> modelMapper.map(element, targetClass)).toList();
    }

}

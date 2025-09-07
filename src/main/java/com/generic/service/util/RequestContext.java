package com.generic.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.generic.service.model.GenericLoggedInUserData;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Log4j2
public class RequestContext {
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static GenericLoggedInUserData getUserFromRequestContextHolder() {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null && attributes.getAttribute(GenericLoggedInUserData.class.getName(), RequestAttributes.SCOPE_REQUEST) != null) {
            return attributes != null ? (GenericLoggedInUserData) attributes.getAttribute(GenericLoggedInUserData.class.getName(), RequestAttributes.SCOPE_REQUEST) : new GenericLoggedInUserData();
        }
        return new GenericLoggedInUserData();
    }

    public static void setUserFromRequestContextHolder(final GenericLoggedInUserData genericLoggedInUserData) {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        attributes.setAttribute(GenericLoggedInUserData.class.getName(), genericLoggedInUserData, RequestAttributes.SCOPE_REQUEST);
    }

    public static String convertObjectToJsonString(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error("Error during write value as string ", e);
            return null;
        }
    }

    public static <TARGET_TYPE> TARGET_TYPE convertJsonStringToObject(String json, Class<TARGET_TYPE> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            log.error("Error during read value as object ", e);
            return null;
        }
    }
}

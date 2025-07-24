package com.generic.service.util;

import com.generic.service.model.GenericLoggedInUserData;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

public class RequestContext {
    public static GenericLoggedInUserData getUserFromRequestContextHolder() {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null && attributes.getAttribute(GenericLoggedInUserData.class.getName(), RequestAttributes.SCOPE_REQUEST) != null) {
            final GenericLoggedInUserData genericLoggedInUserData = attributes != null ? (GenericLoggedInUserData) attributes.getAttribute("genericLoggedInUserModel", RequestAttributes.SCOPE_REQUEST) : null;
            return Optional.of(genericLoggedInUserData).orElse(new GenericLoggedInUserData());
        }
        return new GenericLoggedInUserData();
    }

    public static void setUserFromRequestContextHolder(final GenericLoggedInUserData genericLoggedInUserData) {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        attributes.setAttribute(GenericLoggedInUserData.class.getName(), genericLoggedInUserData, RequestAttributes.SCOPE_REQUEST);
    }
}

package com.generic.service.util;

import com.generic.service.mode.GenericLoggedInUserModel;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

public class RequestContext {
    public static GenericLoggedInUserModel getUserFromRequestContextHolder() {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null && attributes.getAttribute("genericLoggedInUserModel", RequestAttributes.SCOPE_REQUEST) != null) {
            final GenericLoggedInUserModel genericLoggedInUserModel = attributes != null ? (GenericLoggedInUserModel) attributes.getAttribute("genericLoggedInUserModel", RequestAttributes.SCOPE_REQUEST) : null;
            return Optional.of(genericLoggedInUserModel).orElse(new GenericLoggedInUserModel());
        }
        return new GenericLoggedInUserModel();
    }
}

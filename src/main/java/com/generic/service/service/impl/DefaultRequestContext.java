package com.generic.service.service.impl;

import com.generic.service.model.GenericLoggedInUserData;
import com.generic.service.service.RequestContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class DefaultRequestContext implements RequestContext {
    @Override
    public GenericLoggedInUserData getUserFromRequestContextHolder() {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null && attributes.getAttribute(GenericLoggedInUserData.class.getName(), RequestAttributes.SCOPE_REQUEST) != null) {
            return attributes != null ? (GenericLoggedInUserData) attributes.getAttribute(GenericLoggedInUserData.class.getName(), RequestAttributes.SCOPE_REQUEST) : new GenericLoggedInUserData();
        }
        return new GenericLoggedInUserData();
    }

    @Override
    public void setUserIntoRequestContextHolder(GenericLoggedInUserData genericLoggedInUserData) {
        final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        attributes.setAttribute(GenericLoggedInUserData.class.getName(), genericLoggedInUserData, RequestAttributes.SCOPE_REQUEST);
    }
}

package com.generic.service.service;

import com.generic.service.model.GenericLoggedInUserData;

public interface RequestContext {
    GenericLoggedInUserData getUserFromRequestContextHolder();

    void setUserIntoRequestContextHolder(GenericLoggedInUserData genericLoggedInUserData);
}

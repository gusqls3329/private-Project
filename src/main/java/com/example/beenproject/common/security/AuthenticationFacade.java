package com.example.beenproject.common.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthenticationFacade {

    public SecurityUserDetails getLoginUser() {
        return (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getLoginUserPk() {
        return getLoginUser().getSecurityPrincipal().getIuser();
    }

}

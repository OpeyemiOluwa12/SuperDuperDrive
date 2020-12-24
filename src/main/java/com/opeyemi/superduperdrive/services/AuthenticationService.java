package com.opeyemi.superduperdrive.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService  implements AuthenticationProvider {

    private final HashService hashService;

    public AuthenticationService(HashService hashService) {
        this.hashService = hashService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

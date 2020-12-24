package com.opeyemi.superduperdrive.services;

import com.opeyemi.superduperdrive.mapper.UsersMapper;
import com.opeyemi.superduperdrive.model.Users;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private final HashService hashService;
    private final UsersMapper usersMapper;

    public AuthenticationService(HashService hashService, UsersMapper usersMapper) {
        this.hashService = hashService;
        this.usersMapper = usersMapper;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Users user = usersMapper.getUser(username);
        if (user != null) {
            String salt = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, salt);

            if (user.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

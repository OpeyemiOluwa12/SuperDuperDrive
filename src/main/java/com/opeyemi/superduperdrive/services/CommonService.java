package com.opeyemi.superduperdrive.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    private final UserService userService;
    private Integer userId = null;

    public CommonService(UserService userService) {
        this.userService = userService;
    }

    public int getUserId() {
        if (userId == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            return userService.getUser(currentUsername).getUserId();
        } else return userId;
    }
}

package com.opeyemi.superduperdrive.controller;

import com.opeyemi.superduperdrive.model.Users;
import com.opeyemi.superduperdrive.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signup() {
        return "/signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute Users users, Model model) {
        String signUpError = null;

        if (userService.isUserAvailable(users.getUsername())) {
            signUpError = "Username already exist";
        }
        if (signUpError == null) {
            int rowsAdded = userService.createUser(users);
            if (rowsAdded < 0) {
                signUpError = "Signup error occurred please try again";
            }
        }
        if (signUpError == null) {
            model.addAttribute("signUpSuccess", true);
        } else {
            model.addAttribute("signUpError", signUpError);
        }
        return "/signup";
    }
}

package com.udaan.udaan_project.controller;

import com.udaan.udaan_project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.udaan.udaan_project.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @ModelAttribute("loggedInUserFirstName")
    public String getLoggedInUserFirstName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            User user = userService.findByEmail(authentication.getName());
           return (user != null) ? user.getFirstName() : null;
        }
        return null;
    }

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            return userService.findByEmail(authentication.getName());
        }
        return null;
    }
}
package com.udaan.udaan_project.controller;

import com.udaan.udaan_project.dto.UserDto;
import com.udaan.udaan_project.model.User;
import com.udaan.udaan_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
 
    @PostMapping("/signup")
    public RedirectView registerUser(UserDto userDto, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            userService.save(userDto);
            redirectAttributes.addFlashAttribute("signinSuccess", "Account created successfully! Please sign in.");
            redirectAttributes.addFlashAttribute("showSignin", true); // To open signin modal
        } catch (DataIntegrityViolationException e) {
            // This exception is thrown for unique constraint violation (duplicate email)
            redirectAttributes.addFlashAttribute("signupError", "This email is already registered. Please use a different email or sign in.");
            redirectAttributes.addFlashAttribute("showSignup", true); // To reopen the signup modal
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("signupError", "An unexpected error occurred. Please try again.");
            redirectAttributes.addFlashAttribute("showSignup", true);
        }
        String referer = request.getHeader("Referer");
        return new RedirectView(referer != null ? referer : "/");
    }
}
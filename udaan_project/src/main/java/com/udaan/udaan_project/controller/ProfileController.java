package com.udaan.udaan_project.controller;

import com.udaan.udaan_project.model.User;
import com.udaan.udaan_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepository.findByEmail(currentUserName);
        model.addAttribute("currentUser", currentUser);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String firstName, @RequestParam String lastName, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(authentication.getName());

        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        userRepository.save(currentUser);

        redirectAttributes.addAttribute("success", "Profile updated successfully!");
        return "redirect:/profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByEmail(authentication.getName());

        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            redirectAttributes.addAttribute("error", "Incorrect current password.");
            return "redirect:/profile";
        }
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addAttribute("error", "New passwords do not match.");
            return "redirect:/profile";
        }

        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);

        redirectAttributes.addAttribute("success", "Password changed successfully!");
        return "redirect:/profile";
    }
}
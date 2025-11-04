package com.udaan.udaan_project.controller;

import com.udaan.udaan_project.model.User;
import com.udaan.udaan_project.service.CareerSuggestionService;
import com.udaan.udaan_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CareerSuggestionService careerSuggestionService;

    @GetMapping("/")
    public String home() {
        return "index"; 
    }

    @GetMapping("/about")
    public String about() {
        return "about"; 
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact"; 
    }

    @GetMapping("/career-test")
    public String careerTest() {
        return "career-test"; 
    }

    @GetMapping("/resources")
    public String resources() {
        return "resources";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User currentUser = userService.findByEmail(authentication.getName());
            model.addAttribute("currentUser", currentUser);
        }
        return "dashboard";
    }

    @PostMapping("/career-results")
    public String getCareerResults(
            @RequestParam(value = "skill", required = false) List<String> skills,
            @RequestParam("educationLevel") String educationLevel,
            Model model,
            Authentication authentication) {
        List<String> suggestedCareers = careerSuggestionService.suggestCareers(skills, educationLevel);
        model.addAttribute("suggestedCareers", suggestedCareers);
        model.addAttribute("selectedSkills", skills);

        // Save skills and recommendations to the logged-in user's profile
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userService.findByEmail(email);
            if (user != null) {
                userService.updateUserSkillsAndRecommendations(email, skills, suggestedCareers);
            }
        }

        return "career-results";
    }

}

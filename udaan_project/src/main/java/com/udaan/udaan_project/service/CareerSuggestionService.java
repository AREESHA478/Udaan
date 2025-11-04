package com.udaan.udaan_project.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CareerSuggestionService {

    // This map holds career suggestions based on skills for Matric students.
    private static final Map<String, List<String>> MATRIC_CAREERS = Map.of(
            "science", List.of("Pre-Engineering", "Pre-Medical"),
            "biology", List.of("Pre-Medical", "Biotechnology"),
            "computer", List.of("ICS (Computer Science)", "Diploma in IT"),
            "arts", List.of("Fine Arts", "Graphic Design"),
            "commerce", List.of("I.Com (Commerce)")
    );

    // This map holds career suggestions based on skills for Intermediate students.
    private static final Map<String, List<String>> INTER_CAREERS = Map.of(
            "pre-eng", List.of("Mechanical Engineering", "Software Engineering", "Civil Engineering"),
            "pre-med", List.of("MBBS (Doctor)", "Pharmacy (Pharm-D)", "Dentistry (BDS)"),
            "ics", List.of("BS Computer Science", "BS Software Engineering", "BS Data Science"),
            "i-com", List.of("BBA (Business Administration)", "BS Accounting & Finance", "Chartered Accountancy (CA)"),
            "humanities", List.of("BS Sociology", "Law (LLB)", "BS International Relations")
    );

    // This map holds career suggestions based on skills for University students.
    private static final Map<String, List<String>> UNI_CAREERS = Map.of(
            "software", List.of("AI Specialist", "Cybersecurity Analyst", "DevOps Engineer"),
            "business-mgt", List.of("Product Manager", "Marketing Director", "HR Manager"),
            "medical-health", List.of("Clinical Specialist", "Medical Researcher", "Public Health Officer"),
            "creative-design", List.of("UI/UX Lead", "Creative Director", "Animator"),
            "social-sciences", List.of("Policy Advisor", "International Diplomat", "Social Researcher")
    );

    public List<String> suggestCareers(List<String> skills, String educationLevel) {
        if (skills == null || skills.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, List<String>> careerMap;

        // Select the appropriate career map based on the user's education level
        switch (educationLevel) {
            case "matric":
                careerMap = MATRIC_CAREERS;
                break;
            case "inter":
                careerMap = INTER_CAREERS;
                break;
            case "uni":
                careerMap = UNI_CAREERS;
                break;
            default:
                return List.of("Invalid education level selected.");
        }

        // Collect and return unique career suggestions based on the selected skills
        return skills.stream()
                .flatMap(skill -> careerMap.getOrDefault(skill, Collections.emptyList()).stream())
                .distinct()
                .collect(Collectors.toList());
    }
}

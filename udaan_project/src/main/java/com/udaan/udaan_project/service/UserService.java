package com.udaan.udaan_project.service;

import com.udaan.udaan_project.dto.UserDto;
import com.udaan.udaan_project.model.User;

import java.util.List;

public interface UserService {
    User save(UserDto userDto);
    User findByEmail(String email);
    void updateUserSkillsAndRecommendations(String email, List<String> skills, List<String> recommendations);
}

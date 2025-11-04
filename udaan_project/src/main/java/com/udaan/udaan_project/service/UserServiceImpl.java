package com.udaan.udaan_project.service;

import com.udaan.udaan_project.dto.UserDto;
import com.udaan.udaan_project.model.User;
import com.udaan.udaan_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(UserDto userDto) {
        User user = new User(
            userDto.getFirstName(),
            userDto.getLastName(),
            userDto.getEmail(),
            passwordEncoder.encode(userDto.getPassword()) // Password hash karna
        );
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUserSkillsAndRecommendations(String email, List<String> skills, List<String> recommendations) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (skills != null && !skills.isEmpty()) {
                user.setSkills(String.join(", ", skills));
            }
            if (recommendations != null && !recommendations.isEmpty()) {
                user.setCareerRecommendations(String.join(", ", recommendations));
            }
            userRepository.save(user);
        }
    }
}

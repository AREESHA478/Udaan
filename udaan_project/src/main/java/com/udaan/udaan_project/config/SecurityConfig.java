package com.udaan.udaan_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        // This handler redirects back to the referring page with an "?error" parameter
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setUseForward(false); // Ensure it's a redirect
        // Fallback to home page with error if referer is not available
        failureHandler.setDefaultFailureUrl("/?error");
        return failureHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/career-results", "/profile/**", "/contact")) // Disable CSRF for specific POST requests
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/", "/index", "/signup", "/login", "/css/**", "/js/**", "/images/**",
                    "/contact", "/about", "/career-test", "/career-results"
                ).permitAll()
                // All other requests require the user to be authenticated
                .anyRequest().authenticated() 
            )
            .formLogin(
                form -> form
                    // When login is required, redirect to the home page.
                    // The user can then click the icon to open the modal.
                    .loginPage("/")
                    .loginProcessingUrl("/login") 
                    // On successful login, redirect to the home page
                    .defaultSuccessUrl("/", true)
                    // Use our custom failure handler for failed logins
                    .failureHandler(authenticationFailureHandler())
                    .permitAll()
            )
            .logout(
                logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/?logout") // Redirect to home page on logout
                    .permitAll()
            );
        return http.build();
    }
}
package com.askar.videolibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.askar.videolibrary.entity.enums.Role.ADMIN;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((requests) -> requests.
                requestMatchers("/login", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**", "/films", "/directors", "/styles/*").permitAll()
                .requestMatchers("/users/{id}/delete", "/users/{id}/update",
                        "/films/{id}/delete", "/films/{id}/update", "/films/{id}/create-film",
                        "/directors/{id}/update", "/directors/{id}/delete", "/directors/{id}/create-director").hasAuthority(ADMIN.getAuthority())
                .requestMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                .anyRequest().authenticated()
        );
        http.formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/films"));
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

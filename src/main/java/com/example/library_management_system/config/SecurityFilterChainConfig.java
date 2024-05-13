package com.ajosavings.ajosavigs.security;

import com.ajosavings.ajosavigs.configuration.JwtFilterConfig;
import com.ajosavings.ajosavigs.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityFilterChainConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtFilterConfig filterConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests(configurer->
                        configurer
                                .requestMatchers(
                                        "/v3/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html")
                                .permitAll()

                                .requestMatchers(POST,"/api/v1/auth/userReg").permitAll()
                                .requestMatchers("/api/v1/signup/**", "/api/v1/transaction/**","api/savings/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/api/v1/signup/normal").permitAll()
                                .requestMatchers("/api/v1/auth/**", "/api/message/**").permitAll()
                                .requestMatchers("/api/v1/**").permitAll()
                                .requestMatchers("/api/v1/signup/verify-otp").permitAll()
                                .requestMatchers("/api/v1/auth/**", "/update/**", "api/ajoGroup/**").permitAll()
                                .requestMatchers(POST,"/api/v1/auth/registered-user","/api/v1/auth/logout").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/v1/signup/normal", "api/v1/auth/forgot").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/signup/google").permitAll()
                                .requestMatchers(HttpMethod.POST, "api/v1/user/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "api/v1/admin/**").hasRole("ADMIN")

                )
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(filterConfiguration, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:5174", "http://localhost:5173", "http://localhost:3000", "http://127.0.0.1:5173"));
        configuration.setAllowedMethods(List.of("POST", "GET", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


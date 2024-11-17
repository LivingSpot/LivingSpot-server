package com.ssafy.living_spot.common.config;

import com.ssafy.living_spot.auth.jwt.component.JwtUtil;
import com.ssafy.living_spot.auth.exception.CustomAuthenticationEntryPoint;
import com.ssafy.living_spot.auth.filter.CustomLoginFilter;
import com.ssafy.living_spot.auth.filter.JwtAuthenticationFilter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] SWAGGER_URL = {
            "/swagger-resources/**",
            "/favicon.ico",
            "/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/docs/swagger-ui/index.html",
            "/swagger-ui/swagger-ui.css",
    };

    private static final String[] AUTH_WHITELIST = {
            "/**"
    };
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(SWAGGER_URL).permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAt(new CustomLoginFilter(
                                authenticationManager(authenticationConfiguration),
                                jwtUtil,
                                customAuthenticationEntryPoint),
                        UsernamePasswordAuthenticationFilter.class)

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

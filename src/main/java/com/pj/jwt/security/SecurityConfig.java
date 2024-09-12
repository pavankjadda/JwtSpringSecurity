package com.pj.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Creates AuthenticationManager bean that will be used by SecurityFilterChain and Filters
     *
     * @author Pavan Kumar Jadda
     * @since 2.7.16
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * DAO Authentication Provider that provides internal accounts for login. Only to be used in Dev and Test environments
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    public CustomDaoAuthenticationProvider daoAuthenticationProvider() {
        var provider = new CustomDaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Ignore authentication for static files requests
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/static/**");
    }

    /**
     * Defines SecurityFilterChain that authenticates Angular client users
     *
     * @author Pavan Kumar Jadda
     * @since 2.0.0
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).authorizeHttpRequests(
                        registry -> registry.requestMatchers("/api/v1/user/login", "/api/v1/user/authenticate", "/api/v1/user/logout", "/h2-console/**").permitAll()
                                .anyRequest().authenticated()).authenticationProvider(daoAuthenticationProvider()).httpBasic(withDefaults())
                .logout(logout -> logout.deleteCookies("SESSION").invalidateHttpSession(true).clearAuthentication(true));

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        // Uses CorsConfigurationSource bean defined below
        http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));

        // Use CookieCsrfTokenRepository to issue cookie based CSRF(XSRF) tokens
        http.csrf(AbstractHttpConfigurer::disable).cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));

        //Create new session only if required and Sets maximum sessions to 100 and configures Session Registry bean
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }

    //Cors filter to accept incoming requests
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
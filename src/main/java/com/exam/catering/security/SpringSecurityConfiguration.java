package com.exam.catering.security;

import com.exam.catering.security.filter.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
@Configuration
public class SpringSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SpringSecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
//                                 .requestMatchers(HttpMethod.GET, "/client/{id}").hasRole("USER")
//                                .requestMatchers(HttpMethod.GET, "/client/lastName/{lastName}").hasRole("USER")
//                                 .requestMatchers(HttpMethod.GET, "/client/**").hasRole("ADMIN")
//                                 .requestMatchers(HttpMethod.POST, "/client/**").permitAll()
//                                 .requestMatchers(HttpMethod.PUT, "/client/**").permitAll()
//                                 .requestMatchers(HttpMethod.DELETE, "/client/{id}").hasRole("ADMIN")
//
//                                 .requestMatchers(HttpMethod.GET, "/dishes/**").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.POST, "/dishes/**").hasRole("ADMIN")
//                                .requestMatchers(HttpMethod.PUT, "/dishes/**").hasRole("ADMIN")
//                                 .requestMatchers(HttpMethod.DELETE, "/dishes/**").hasRole("ADMIN")
//
//                                .requestMatchers(HttpMethod.GET, "/menu").permitAll()
//
//                                .requestMatchers(HttpMethod.GET, "/orders/{id}").hasRole("USER")
//                                .requestMatchers(HttpMethod.GET, "/orders/**").hasRole("ADMIN")
//                                 .requestMatchers(HttpMethod.POST, "/orders").permitAll()
//                                 .requestMatchers(HttpMethod.PUT, "/orders").permitAll()
//                                 .requestMatchers(HttpMethod.DELETE, "/orders/{id}").permitAll()
//
//                                .requestMatchers(HttpMethod.GET, "/orderedMenu/**").permitAll()
//                                .requestMatchers(HttpMethod.POST, "/orderedMenu/{orderId}").permitAll()
//                                .requestMatchers(HttpMethod.PUT, "/orderedMenu").permitAll()
//                                .requestMatchers(HttpMethod.DELETE, "/orderedMenu/{id}").permitAll()
//
//                                 .requestMatchers(HttpMethod.POST, "/file/**").hasRole("ADMIN")
//                                 .requestMatchers(HttpMethod.DELETE, "/file/{filename}").hasRole("ADMIN")
//                                 .requestMatchers(HttpMethod.GET, "/file/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/authentication").permitAll()
                                .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.PatitasFelices.SistemaDeVeterinario.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,
                                    JwtService jwtService,
                                    CuentaUserDetailsService userDetailsService) throws Exception {

        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtService, userDetailsService);

        http

                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/logout", "/h2-console/**"))


                .headers(h -> h.frameOptions(f -> f.sameOrigin()))

                .authorizeHttpRequests(a -> a

                        .requestMatchers("/login", "/registro", "/acceso-denegado",
                                "/img/**", "/css/**", "/js/**", "/error").permitAll()

                        .requestMatchers("/h2-console/**").hasRole("ADMIN")

                        .requestMatchers("/").authenticated()

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/usuario/**").hasRole("CLIENTE")

                        .anyRequest().authenticated())

                .exceptionHandling(e -> e
                        .authenticationEntryPoint((req, res, ex) -> res.sendRedirect("/login"))
                        .accessDeniedPage("/acceso-denegado"))

                .logout(l -> l.disable())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
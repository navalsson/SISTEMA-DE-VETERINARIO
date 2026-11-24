package com.PatitasFelices.SistemaDeVeterinario.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String COOKIE = "JWT";

    private final JwtService jwtService;
    private final CuentaUserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, CuentaUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = leerCookie(request);

        if (token != null && jwtService.esValido(token)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails u = userDetailsService.loadUserByUsername(jwtService.extraerUsuario(token));
                var auth = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (UsernameNotFoundException ignorado) {

            }
        }
        chain.doFilter(request, response);
    }

    private String leerCookie(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie c : request.getCookies()) {
            if (COOKIE.equals(c.getName())) return c.getValue();
        }
        return null;
    }
}
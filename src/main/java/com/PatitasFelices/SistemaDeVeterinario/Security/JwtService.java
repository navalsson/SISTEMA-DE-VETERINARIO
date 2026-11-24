package com.PatitasFelices.SistemaDeVeterinario.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiracion-ms}")
    private long expiracionMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String generar(UserDetails usuario) {
        String rol = usuario.getAuthorities().iterator().next().getAuthority();
        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiracionMs))
                .signWith(key())
                .compact();
    }


    public String extraerUsuario(String token) {
        return claims(token).getSubject();
    }


    public boolean esValido(String token) {
        try {
            claims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims claims(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload();
    }

    public long getExpiracionMs() {
        return expiracionMs;
    }
}
package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Security.JwtAuthFilter;
import com.PatitasFelices.SistemaDeVeterinario.Security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;

@Controller
public class AuthController {

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtService jwtService;

    @GetMapping("/login")
    public String mostrarLogin(Authentication auth) {

        if (auth != null && auth.getPrincipal() instanceof UserDetails u) {
            return destino(u);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpServletResponse response,
                        Model model) {
        try {

            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.trim(), password));
            UserDetails u = (UserDetails) auth.getPrincipal();


            ResponseCookie cookie = ResponseCookie.from(JwtAuthFilter.COOKIE, jwtService.generar(u))
                    .httpOnly(true)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofMillis(jwtService.getExpiracionMs()))
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return destino(u);

        } catch (AuthenticationException e) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        ResponseCookie borrar = ResponseCookie.from(JwtAuthFilter.COOKIE, "")
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, borrar.toString());

        return "redirect:/login?logout";
    }

    @RequestMapping("/acceso-denegado")
    public String denegado() {
        return "acceso-denegado";
    }


    private String destino(UserDetails u) {
        boolean admin = u.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return admin ? "redirect:/admin/metricas" : "redirect:/usuario/inicio";
    }
}
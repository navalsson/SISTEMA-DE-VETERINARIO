package com.PatitasFelices.SistemaDeVeterinario.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {


    @GetMapping("/")
    public String raiz(Authentication auth) {
        boolean admin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return admin ? "redirect:/admin/metricas" : "redirect:/usuario/inicio";
    }

    @GetMapping("/usuario/inicio")
    public String inicio() {
        return "usuario/inicio";
    }

    @GetMapping("/usuario/contacto")
    public String contacto() {
        return "usuario/contacto";
    }
}
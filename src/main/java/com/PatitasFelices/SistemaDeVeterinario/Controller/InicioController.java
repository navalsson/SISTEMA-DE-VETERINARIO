package com.PatitasFelices.SistemaDeVeterinario.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping({"/", "/usuario/inicio"})
    public String inicio() {
        return "usuario/inicio";
    }
    @GetMapping("/usuario/contacto")
    public String contacto() {
        return "usuario/contacto";
    }
}
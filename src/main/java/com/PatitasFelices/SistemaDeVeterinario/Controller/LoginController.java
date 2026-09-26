package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Administrador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    // Lista dummy de administradores válidos (simula la "base de datos")
    private List<Administrador> administradores = new ArrayList<>();

    public LoginController() {
        administradores.add(new Administrador(1L, "admin@patitasfelices.com", "admin123"));
    }

    // Muestra el formulario (cuando alguien visita la página)
    @GetMapping("/admin/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("administrador", new Administrador());
        return "admin/login";
    }

    // Procesa el formulario (cuando alguien presiona "Iniciar Sesión")
    @PostMapping("/admin/login")
    public String procesarLogin(@ModelAttribute Administrador administrador, Model model) {

        boolean credencialesValidas = administradores.stream()
                .anyMatch(a -> a.getUsuario().equals(administrador.getUsuario())
                        && a.getPassword().equals(administrador.getPassword()));

        if (credencialesValidas) {
            return "redirect:/admin/metricas";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            model.addAttribute("administrador", new Administrador());
            return "admin/login";
        }
    }
}

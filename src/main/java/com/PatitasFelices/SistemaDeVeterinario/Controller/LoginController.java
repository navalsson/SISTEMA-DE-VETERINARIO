package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Administrador;
import com.PatitasFelices.SistemaDeVeterinario.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/admin/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("administrador", new Administrador());
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String procesarLogin(@ModelAttribute Administrador administrador, Model model) {
        if (loginService.validar(administrador)) return "redirect:/admin/metricas";
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        model.addAttribute("administrador", new Administrador());
        return "admin/login";
    }
}
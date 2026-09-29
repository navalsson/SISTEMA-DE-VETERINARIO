package com.PatitasFelices.SistemaDeVeterinario.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private ServicioController servicioController;

    // Muestra los servicios disponibles para el usuario
    @GetMapping("/servicios")
    public String mostrarServicios(Model model) {

        model.addAttribute("servicios",
                servicioController.obtenerListaServicios());

        return "usuario/servicios";
    }
}
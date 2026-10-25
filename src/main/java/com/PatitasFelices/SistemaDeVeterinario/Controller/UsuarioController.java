package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.service.ProductoService;
import com.PatitasFelices.SistemaDeVeterinario.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired private ServicioService servicioService;
    @Autowired private ProductoService productoService;

    @GetMapping("/servicios")
    public String mostrarServicios(Model model) {
        model.addAttribute("servicios", servicioService.listar());
        model.addAttribute("productos", productoService.listar());   // catálogo de farmacia
        return "usuario/servicios";
    }
}
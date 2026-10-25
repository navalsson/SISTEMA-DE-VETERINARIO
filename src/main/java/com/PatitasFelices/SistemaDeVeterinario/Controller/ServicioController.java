package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Servicio;
import com.PatitasFelices.SistemaDeVeterinario.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "servicios");
        model.addAttribute("servicios", servicioService.listar());
        return "admin/servicios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paginaActiva", "servicios");
        model.addAttribute("servicio", new Servicio());
        return "admin/formulario-servicio";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("paginaActiva", "servicios");
        model.addAttribute("servicio", servicioService.buscarPorId(id));
        return "admin/formulario-servicio";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Servicio servicio, BindingResult result, Model model) {

        Servicio existente = servicioService.buscarPorNombre(servicio.getNombre());
        if (existente != null && !existente.getId().equals(servicio.getId())) {
            result.rejectValue("nombre", "duplicado", "Ya existe un servicio con ese nombre");
        }
        if (result.hasErrors()) {
            model.addAttribute("paginaActiva", "servicios");
            return "admin/formulario-servicio";
        }
        servicioService.guardar(servicio);
        return "redirect:/admin/servicios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicioService.eliminar(id);
        return "redirect:/admin/servicios";
    }
}

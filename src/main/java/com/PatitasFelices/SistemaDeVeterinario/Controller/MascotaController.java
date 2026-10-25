package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import com.PatitasFelices.SistemaDeVeterinario.service.ClienteService;
import com.PatitasFelices.SistemaDeVeterinario.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.PatitasFelices.SistemaDeVeterinario.service.ReglaNegocioService;


import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/mascotas")
public class MascotaController {

    @Autowired private MascotaService mascotaService;
    @Autowired private ClienteService clienteService;
    @Autowired private ReglaNegocioService reglaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "mascotas");
        model.addAttribute("mascotas", mascotaService.listar());
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("reglas", reglaService);
        return "admin/mascotas";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paginaActiva", "mascotas");
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("clientes", clienteService.listar());
        return "admin/formulario-mascota";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("paginaActiva", "mascotas");
        model.addAttribute("mascota", mascotaService.buscarPorId(id));
        model.addAttribute("clientes", clienteService.listar());
        return "admin/formulario-mascota";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Mascota mascota, BindingResult result, Model model,
                          @RequestParam(defaultValue = "meses") String unidadEdad) {
        if ("anios".equals(unidadEdad)) mascota.setEdad(mascota.getEdad() * 12);
        if (mascota.getEdad() > 360) {
            result.rejectValue("edad", "max", "La edad máxima es 30 años (360 meses)");
        }
        if (result.hasErrors()) {
            model.addAttribute("paginaActiva", "mascotas");
            model.addAttribute("clientes", clienteService.listar());
            return "admin/formulario-mascota";
        }
        mascotaService.guardar(mascota);
        return "redirect:/admin/mascotas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return "redirect:/admin/mascotas";
    }
}
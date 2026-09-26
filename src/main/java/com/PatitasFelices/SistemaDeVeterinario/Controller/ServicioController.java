package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Servicio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/servicios")
public class ServicioController {
    private List<Servicio> servicios = new ArrayList<>();
    private Long siguienteId = 1L;

    public ServicioController() {
        servicios.add(new Servicio(siguienteId++, "Consulta Médica General", "Servicio Médico", 30.00, "Activo"));
        servicios.add(new Servicio(siguienteId++, "Vacunación y Refuerzo", "Servicio Médico", 80.00, "Activo"));
        servicios.add(new Servicio(siguienteId++, "Baño y Corte de Uñas", "Estética", 50.00, "Activo"));
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "servicios");
        model.addAttribute("servicios", servicios);
        return "admin/servicios";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paginaActiva", "servicios");
        model.addAttribute("servicio", new Servicio());
        return "admin/formulario-servicio";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Servicio servicio = servicios.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(new Servicio());

        model.addAttribute("paginaActiva", "servicios");
        model.addAttribute("servicio", servicio);
        return "admin/formulario-servicio";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Servicio servicio) {
        if (servicio.getId() == null) {
            servicio.setId(siguienteId++);
            servicios.add(servicio);
        } else {
            servicios.removeIf(s -> s.getId().equals(servicio.getId()));
            servicios.add(servicio);
        }
        return "redirect:/admin/servicios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicios.removeIf(s -> s.getId().equals(id));
        return "redirect:/admin/servicios";
    }

    // Método auxiliar público para que CitaController pueda usar esta lista
    public List<Servicio> obtenerListaServicios() {
        return servicios;
    }
}

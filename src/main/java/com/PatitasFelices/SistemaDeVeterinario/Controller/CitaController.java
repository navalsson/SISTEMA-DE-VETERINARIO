package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/citas")
public class CitaController {
    private List<Cita> citas = new ArrayList<>();
    private Long siguienteId = 1L;

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private MascotaController mascotaController;

    @Autowired
    private ServicioController servicioController;

    public CitaController() {
        citas.add(new Cita(siguienteId++, 1L, 1L, 1L,
                LocalDateTime.of(2026, 9, 3, 11, 0), "Confirmada", "Tarjeta", 30.00));
        citas.add(new Cita(siguienteId++, 2L, 2L, 2L,
                LocalDateTime.of(2026, 9, 3, 9, 30), "Confirmada", "Efectivo", 80.00));
        citas.add(new Cita(siguienteId++, 3L, 3L, 3L,
                LocalDateTime.of(2026, 9, 1, 16, 0), "Confirmada", "Efectivo", 50.00));
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "citas");
        model.addAttribute("citas", citas);
        model.addAttribute("clientes", clienteController.obtenerListaClientes());
        model.addAttribute("mascotas", mascotaController.obtenerListaMascotas());
        model.addAttribute("servicios", servicioController.obtenerListaServicios());
        return "admin/citas";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Cita cita = citas.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(new Cita());
        model.addAttribute("paginaActiva", "citas");
        model.addAttribute("cita", cita);
        model.addAttribute("clientes", clienteController.obtenerListaClientes());
        model.addAttribute("mascotas", mascotaController.obtenerListaMascotas());
        model.addAttribute("servicios", servicioController.obtenerListaServicios());
        return "admin/detalle-cita";
    }

    @GetMapping("/estado/{id}")
    public String mostrarCambiarEstado(@PathVariable Long id, Model model) {
        Cita cita = citas.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(new Cita());
        model.addAttribute("paginaActiva", "citas");
        model.addAttribute("cita", cita);
        return "admin/cambiar-estado";
    }

    @PostMapping("/estado/guardar")
    public String guardarEstado(@ModelAttribute Cita cita) {
        citas.stream()
                .filter(c -> c.getId().equals(cita.getId()))
                .findFirst()
                .ifPresent(c -> c.setEstado(cita.getEstado()));
        return "redirect:/admin/citas";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id) {
        citas.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(c -> c.setEstado("Cancelada"));
        return "redirect:/admin/citas";
    }
    public List<Cita> obtenerListaCitas() {
        return citas;
    }
}

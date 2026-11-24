package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import com.PatitasFelices.SistemaDeVeterinario.Model.Servicio;
import com.PatitasFelices.SistemaDeVeterinario.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/citas")
public class CitaController {

    @Autowired private CitaService citaService;
    @Autowired private ClienteService clienteService;
    @Autowired private MascotaService mascotaService;
    @Autowired private ServicioService servicioService;
    @Autowired private ReservaService reservaService;
    @Autowired private HorarioService horarioService;


    private void cargarListas(Model model) {
        model.addAttribute("paginaActiva", "citas");
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("mascotas", mascotaService.listar());
        model.addAttribute("servicios", servicioService.listar());
    }


    private void asegurarRelaciones(Cita cita) {
        if (cita.getCliente() == null) cita.setCliente(new Cliente());
        if (cita.getMascota() == null) cita.setMascota(new Mascota());
        if (cita.getServicio() == null) cita.setServicio(new Servicio());
    }


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "citas");
        model.addAttribute("citas", citaService.listar());
        return "admin/citas";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        model.addAttribute("paginaActiva", "citas");
        model.addAttribute("cita", citaService.buscarPorId(id));
        return "admin/detalle-cita";
    }


    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paginaActiva", "citas");
        model.addAttribute("servicios", servicioService.listarActivos());
        model.addAttribute("horarios", horarioService.listarHorarios(true));
        model.addAttribute("hoy", LocalDate.now());
        return "admin/nueva-cita";
    }

    @PostMapping("/crear")
    public String crear(@RequestParam Long servicioId,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                        @RequestParam String hora,
                        @RequestParam String nombre,
                        @RequestParam String dni,
                        @RequestParam String telefono,
                        @RequestParam String email,
                        @RequestParam String mascota,
                        @RequestParam String especie,
                        @RequestParam(defaultValue = "") String raza,
                        @RequestParam(defaultValue = "0") int edad,
                        @RequestParam(defaultValue = "meses") String unidadEdad,
                        @RequestParam(defaultValue = "") String sexo,
                        @RequestParam(required = false) Double peso,
                        @RequestParam String metodoPago) {

        int edadMeses = "anios".equals(unidadEdad) ? edad * 12 : edad;   // se guarda siempre en meses

        Cita cita = reservaService.reservar(servicioId, fecha, hora, nombre, dni, telefono,
                email, mascota, especie, raza, edadMeses, sexo, peso, metodoPago);

        if (cita == null) return "redirect:/admin/citas/nuevo?error=true";
        return "redirect:/admin/citas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        cargarListas(model);
        Cita cita = citaService.buscarPorId(id);
        asegurarRelaciones(cita);
        model.addAttribute("cita", cita);
        return "admin/formulario-cita";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cita cita, BindingResult result, Model model) {
        asegurarRelaciones(cita);


        if (cita.getCliente().getId() == null)
            result.rejectValue("cliente", "requerido", "Selecciona un cliente");
        if (cita.getMascota().getId() == null)
            result.rejectValue("mascota", "requerido", "Selecciona una mascota");
        if (cita.getServicio().getId() == null)
            result.rejectValue("servicio", "requerido", "Selecciona un servicio");

        // Integridad: la mascota debe pertenecer al cliente elegido
        if (!result.hasErrors()) {
            Mascota m = mascotaService.buscarPorId(cita.getMascota().getId());
            if (m.getId() == null || m.getCliente().getId() == null
                    || !m.getCliente().getId().equals(cita.getCliente().getId())) {
                result.rejectValue("mascota", "dueno", "La mascota no pertenece al cliente seleccionado");
            }
        }

        if (result.hasErrors()) {
            cargarListas(model);
            return "admin/formulario-cita";
        }


        cita.setCliente(clienteService.buscarPorId(cita.getCliente().getId()));
        cita.setMascota(mascotaService.buscarPorId(cita.getMascota().getId()));
        cita.setServicio(servicioService.buscarPorId(cita.getServicio().getId()));
        citaService.guardar(cita);
        return "redirect:/admin/citas";
    }


    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return "redirect:/admin/citas";
    }
}
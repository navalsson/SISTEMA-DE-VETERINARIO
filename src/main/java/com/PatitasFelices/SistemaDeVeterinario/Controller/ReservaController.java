package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class ReservaController {

    @Autowired private ServicioService servicioService;
    @Autowired private HorarioService horarioService;
    @Autowired private ReservaService reservaService;
    @Autowired private ClienteService clienteService;


    private Cliente clienteLogueado(Authentication auth) {
        return auth == null ? null : clienteService.buscarPorUsuario(auth.getName());
    }

    @GetMapping("/reserva")
    public String entrada(@RequestParam(required = false) Long servicioId) {
        return servicioId == null
                ? "redirect:/usuario/reserva1"
                : "redirect:/usuario/reserva2?servicioId=" + servicioId;
    }

    @GetMapping("/reserva1")
    public String paso1(Model model) {
        model.addAttribute("servicios", servicioService.listarActivos());
        return "usuario/reserva1";
    }

    @GetMapping("/reserva2")
    public String paso2(@RequestParam Long servicioId,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                        Authentication auth,
                        Model model) {
        LocalDate hoy = LocalDate.now();
        if (fecha == null || fecha.isBefore(hoy)) fecha = hoy;


        Cliente cliente = clienteLogueado(auth);
        boolean vip = cliente != null && reservaService.esVip(cliente.getDni());
        boolean domingo = fecha.getDayOfWeek() == DayOfWeek.SUNDAY;

        model.addAttribute("servicioId", servicioId);
        model.addAttribute("fecha", fecha);
        model.addAttribute("vip", vip);
        model.addAttribute("domingo", domingo);
        model.addAttribute("hoy", hoy);                      // fecha mínima del calendario
        model.addAttribute("maxima", hoy.plusDays(30));      // fecha máxima del calendario
        model.addAttribute("horarios", domingo ? List.of() : horarioService.listarHorarios(vip));
        model.addAttribute("ocupados", horarioService.ocupados(fecha));
        return "usuario/reserva2";
    }

    @GetMapping("/reserva3")
    public String paso3(@RequestParam Long servicioId,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                        @RequestParam String hora,
                        Authentication auth,
                        Model model) {
        Cliente cliente = clienteLogueado(auth);
        if (cliente == null) return "redirect:/usuario/inicio";

        model.addAttribute("servicio", servicioService.buscarPorId(servicioId));
        model.addAttribute("fecha", fecha);
        model.addAttribute("hora", hora);
        model.addAttribute("cliente", cliente);   // datos que se muestran ya completos
        return "usuario/reserva3";
    }

    @PostMapping("/reserva/confirmar")
    public String confirmar(Authentication auth,
                            @RequestParam Long servicioId,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                            @RequestParam String hora,
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


        Cliente cliente = clienteLogueado(auth);
        if (cliente == null) return "redirect:/usuario/inicio";

        int edadMeses = "anios".equals(unidadEdad) ? edad * 12 : edad;

        Cita cita = reservaService.reservar(servicioId, fecha, hora,
                cliente.getNombre(), cliente.getDni(), telefono,
                email, mascota, especie, raza, edadMeses, sexo, peso, metodoPago);

        if (cita == null) {
            return "redirect:/usuario/reserva2?servicioId=" + servicioId
                    + "&fecha=" + fecha + "&error=true";
        }
        return "redirect:/usuario/confirmacion/" + cita.getId();
    }
}
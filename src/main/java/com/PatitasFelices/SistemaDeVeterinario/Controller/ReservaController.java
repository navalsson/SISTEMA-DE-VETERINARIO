package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Servicio;
import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReservaController {

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private MascotaController mascotaController;

    // =========================
    // RESERVA 1 (PASO 1)
    // =========================
    @GetMapping("/reserva")
    public String mostrarReserva(Model model) {
        List<Servicio> listaServicios = obtenerServiciosDesdeModelo();
        model.addAttribute("servicios", listaServicios);
        return "usuario/reserva1";
    }

    // =========================
    // RESERVA 2 (PASO 2)
    // =========================
    @GetMapping("/reserva2")
    public String mostrarReserva2(
            @RequestParam(value = "servicioId", required = false) Long servicioId,
            Model model) {
        model.addAttribute("servicioId", servicioId);
        return "usuario/reserva2";
    }

    // =========================
    // RESERVA 3 (PASO 3)
    // =========================
    @GetMapping("/reserva3")
    public String mostrarReserva3(
            @RequestParam(value = "servicioId", required = false) Long servicioId,
            @RequestParam(value = "hora", required = false) String hora,
            Model model) {
        model.addAttribute("servicioId", servicioId);
        model.addAttribute("hora", hora);
        return "usuario/reserva3";
    }

    // =========================
    // CONFIRMACIÓN DE CITA
    // =========================
    @GetMapping("/reserva/confirmacion/{id}")
    public String mostrarCitaConfirmada(@PathVariable("id") Long id, Model model) {

        // 1. Buscamos y cruzamos el Servicio usando nuestra lista local por ID
        Servicio servicioSeleccionado = obtenerServiciosDesdeModelo().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(new Servicio(1L, "Consulta Médica General", "Medicina General", 30.00, "Activo"));

        // 2. Simulamos los datos de la Cita adaptados a las fechas de tus registros de prueba
        Map<String, Object> citaMap = new HashMap<>();
        citaMap.put("id", "#PF-024" + id);
        citaMap.put("metodoPago", id == 1L ? "Yape" : "Efectivo");
        citaMap.put("montoPagado", servicioSeleccionado.getPrecio());
        citaMap.put("estado", "Confirmada");

        if (id == 2L) {
            citaMap.put("fechaHora", LocalDateTime.of(2026, 9, 3, 9, 30));
        } else if (id == 3L) {
            citaMap.put("fechaHora", LocalDateTime.of(2026, 9, 1, 16, 0));
        } else {
            citaMap.put("fechaHora", LocalDateTime.of(2026, 9, 3, 11, 0));
        }

        // 3. Obtenemos el Cliente y la Mascota correspondientes desde tus controladores usando streams de Java
        Cliente clienteObj = clienteController.obtenerListaClientes().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(new Cliente());

        Mascota mascotaObj = mascotaController.obtenerListaMascotas().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(new Mascota());

        // 4. Enviamos de manera exacta los 4 objetos requeridos por tu Thymeleaf
        model.addAttribute("cita", citaMap);
        model.addAttribute("cliente", clienteObj);
        model.addAttribute("mascota", mascotaObj);
        model.addAttribute("servicio", servicioSeleccionado);

        return "usuario/cita-confirmada";
    }

    /**
     * Gener la lista utilizando estrictamente la estructura de tu entidad 'Servicio'
     */
    private List<Servicio> obtenerServiciosDesdeModelo() {
        List<Servicio> servicios = new ArrayList<>();
        servicios.add(new Servicio(1L, "Consulta Médica General", "Medicina General", 30.00, "Activo"));
        servicios.add(new Servicio(2L, "Vacunación y Refuerzo", "Prevención", 80.00, "Activo"));
        servicios.add(new Servicio(3L, "Baño y Corte de Uñas", "Estética", 50.00, "Activo"));
        return servicios;
    }
}
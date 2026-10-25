package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import com.PatitasFelices.SistemaDeVeterinario.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ConfirmacionController {

    @Autowired private CitaService citaService;
    @Autowired private ClienteService clienteService;
    @Autowired private MascotaService mascotaService;
    @Autowired private ServicioService servicioService;
    @Autowired private ReglaNegocioService reglaService;

    @GetMapping("/usuario/confirmacion/{id}")
    public String mostrarConfirmacion(@PathVariable Long id, Model model) {
        Cita cita = citaService.buscarPorId(id);

        List<String> beneficios = new ArrayList<>(
                reglaService.beneficios(clienteService.buscarPorId(cita.getClienteId())));

        Mascota mascota = mascotaService.buscarPorId(cita.getMascotaId());
        if (reglaService.tieneBanoGratis(mascota)) {
            beneficios.add("Baño y Corte de Uñas gratis para " + mascota.getNombre() + " por tener más de 4 meses");
        }

        model.addAttribute("cita", cita);
        model.addAttribute("beneficios", beneficios);
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("mascotas", mascotaService.listar());
        model.addAttribute("servicios", servicioService.listar());
        return "usuario/confirmacion";
    }
}
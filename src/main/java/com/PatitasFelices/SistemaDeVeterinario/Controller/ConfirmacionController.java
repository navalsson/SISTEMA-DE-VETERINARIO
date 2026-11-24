package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import com.PatitasFelices.SistemaDeVeterinario.Model.Servicio;
import com.PatitasFelices.SistemaDeVeterinario.service.CitaService;
import com.PatitasFelices.SistemaDeVeterinario.service.ReglaNegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ConfirmacionController {

    @Autowired private CitaService citaService;
    @Autowired private ReglaNegocioService reglaService;

    @GetMapping("/usuario/confirmacion/{id}")
    public String mostrarConfirmacion(@PathVariable Long id, Model model) {
        Cita cita = citaService.buscarPorId(id);
        if (cita.getId() == null) return "redirect:/usuario/inicio";
        Mascota mascota = cita.getMascota();
        Servicio servicio = cita.getServicio();

        List<String> beneficios = new ArrayList<>(reglaService.beneficios(cita.getCliente()));


        boolean banoGratis = reglaService.esBanoGratis(mascota, servicio);
        if (banoGratis) {
            beneficios.add("Baño gratis para " + mascota.getNombre() + " (más de "
                    + ReglaNegocioService.MESES_BENEFICIO_MASCOTA + " meses)");
        }


        if (cita.getMontoPagado() == 0 && !banoGratis) {
            beneficios.add("Cita gratis por tus 3 citas del mes");
        }

        model.addAttribute("cita", cita);
        model.addAttribute("beneficios", beneficios);
        return "usuario/confirmacion";
    }
}
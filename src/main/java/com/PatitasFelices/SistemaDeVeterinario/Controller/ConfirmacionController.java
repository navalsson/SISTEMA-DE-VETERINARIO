package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ConfirmacionController {

    @Autowired
    private CitaController citaController;

    @Autowired
    private ClienteController clienteController;

    @Autowired
    private MascotaController mascotaController;

    @Autowired
    private ServicioController servicioController;


    @GetMapping("/usuario/confirmacion/{id}")
    public String mostrarConfirmacion(@PathVariable Long id, Model model) {

        Cita cita = citaController.obtenerListaCitas()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(new Cita());

        model.addAttribute("cita", cita);

        model.addAttribute(
                "clientes",
                clienteController.obtenerListaClientes()
        );

        model.addAttribute(
                "mascotas",
                mascotaController.obtenerListaMascotas()
        );

        model.addAttribute(
                "servicios",
                servicioController.obtenerListaServicios()
        );

        return "usuario/confirmacion";
    }
}
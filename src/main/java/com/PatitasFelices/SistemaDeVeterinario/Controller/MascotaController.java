package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import com.PatitasFelices.SistemaDeVeterinario.service.ClienteService;
import com.PatitasFelices.SistemaDeVeterinario.service.MascotaService;
import com.PatitasFelices.SistemaDeVeterinario.service.ReglaNegocioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("reglas", reglaService);
        return "admin/mascotas";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Mascota m = new Mascota();
        m.setCliente(new Cliente());   // para que el select enlace con cliente.id
        model.addAttribute("paginaActiva", "mascotas");
        model.addAttribute("mascota", m);
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

        if (mascota.getCliente() == null || mascota.getCliente().getId() == null) {
            result.rejectValue("cliente", "requerido", "Selecciona el dueño");
        }
        if (result.hasErrors()) {
            model.addAttribute("paginaActiva", "mascotas");
            model.addAttribute("clientes", clienteService.listar());
            return "admin/formulario-mascota";
        }

        mascota.setCliente(clienteService.buscarPorId(mascota.getCliente().getId()));
        mascotaService.guardar(mascota);
        return "redirect:/admin/mascotas";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        String motivo = mascotaService.motivoNoEliminar(id);
        if (motivo != null) {
            ra.addFlashAttribute("error", motivo);
        } else {
            mascotaService.eliminar(id);
        }
        return "redirect:/admin/mascotas";
    }
}
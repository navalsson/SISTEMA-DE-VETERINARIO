package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.service.ClienteService;
import com.PatitasFelices.SistemaDeVeterinario.service.ReglaNegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired private ReglaNegocioService reglaService;


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("reglas", reglaService);
        return "admin/clientes";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("cliente", new Cliente());
        return "admin/formulario-cliente";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        return "admin/formulario-cliente";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult result, Model model) {
        // Unicidad: no puede haber dos clientes con el mismo DNI
        Cliente existente = clienteService.buscarPorDni(cliente.getDni());
        if (existente != null && !existente.getId().equals(cliente.getId())) {
            result.rejectValue("dni", "duplicado", "Ya existe un cliente registrado con ese DNI");
        }
        if (result.hasErrors()) {                       // si hay errores, vuelve al formulario
            model.addAttribute("paginaActiva", "clientes");
            return "admin/formulario-cliente";
        }
        clienteService.guardar(cliente);
        return "redirect:/admin/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return "redirect:/admin/clientes";
    }


}
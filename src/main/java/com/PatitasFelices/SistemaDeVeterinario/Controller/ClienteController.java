package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/clientes")

public class ClienteController {
    private List<Cliente> clientes = new ArrayList<>();
    private Long siguienteId = 1L;

    public ClienteController() {
        clientes.add(new Cliente(siguienteId++, "Juan Pérez", "45678912", "987654321", "juan@example.com",
                3, 90.0, LocalDate.of(2025, 3, 10), 1));
        clientes.add(new Cliente(siguienteId++, "María Torres", "41234567", "945112233", "maria@example.com",
                1, 80.0, LocalDate.of(2026, 1, 5), 1));
        clientes.add(new Cliente(siguienteId++, "Lucía Gómez", "48765432", "912334556", "lucia@example.com",
                6, 350.0, LocalDate.of(2024, 8, 20), 4));

    }

    // Lista de clientes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("clientes", clientes);
        return "admin/clientes";
    }

    // Muestra formulario vacío (Nuevo)
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("cliente", new Cliente());
        return "admin/formulario-cliente";
    }

    // Muestra formulario con datos precargados (Editar)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(new Cliente());

        model.addAttribute("paginaActiva", "clientes");
        model.addAttribute("cliente", cliente);
        return "admin/formulario-cliente";
    }

    // Guarda (crea o actualiza)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        if (cliente.getId() == null) {
            // Es nuevo
            cliente.setId(siguienteId++);
            cliente.setFechaRegistro(LocalDate.now());
            clientes.add(cliente);
        } else {
            // Es edición: quitamos el viejo y agregamos el actualizado
            clientes.removeIf(c -> c.getId().equals(cliente.getId()));
            clientes.add(cliente);
        }
        return "redirect:/admin/clientes";
    }

    // Elimina
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        clientes.removeIf(c -> c.getId().equals(id));
        return "redirect:/admin/clientes";
    }

    public List<Cliente> obtenerListaClientes() {
        return clientes;
    }
}

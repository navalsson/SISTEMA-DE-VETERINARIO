package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/mascotas")
public class MascotaController {
    private List<Mascota> mascotas = new ArrayList<>();
    private Long siguienteId = 1L;

    @Autowired
    private ClienteController clienteController; // le "pedimos prestada" la lista de clientes

    public MascotaController() {
        mascotas.add(new Mascota(siguienteId++, "Firulais", "Perro", "Labrador", "3 años", "Macho", 28.5, 1L));
        mascotas.add(new Mascota(siguienteId++, "Michi", "Gato", "Siamés", "2 años", "Hembra", 4.2, 2L));
        mascotas.add(new Mascota(siguienteId++, "Toby", "Perro", "Criollo", "5 años", "Macho", 15.0, 3L));
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "mascotas");
        model.addAttribute("mascotas", mascotas);
        model.addAttribute("clientes", clienteController.obtenerListaClientes()); // para mostrar el nombre del dueño
        return "admin/mascotas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("paginaActiva", "mascotas");
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("clientes", clienteController.obtenerListaClientes());
        return "admin/formulario-mascota";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Mascota mascota = mascotas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(new Mascota());

        model.addAttribute("paginaActiva", "mascotas");
        model.addAttribute("mascota", mascota);
        model.addAttribute("clientes", clienteController.obtenerListaClientes());
        return "admin/formulario-mascota";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Mascota mascota) {
        if (mascota.getId() == null) {
            mascota.setId(siguienteId++);
            mascotas.add(mascota);
        } else {
            mascotas.removeIf(m -> m.getId().equals(mascota.getId()));
            mascotas.add(mascota);
        }
        return "redirect:/admin/mascotas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        mascotas.removeIf(m -> m.getId().equals(id));
        return "redirect:/admin/mascotas";
    }

    // Método auxiliar para mostrar el nombre del dueño en la tabla (en vez del ID)
    public String obtenerNombreCliente(Long clienteId) {
        return clienteController.obtenerListaClientes().stream()
                .filter(c -> c.getId().equals(clienteId))
                .findFirst()
                .map(c -> c.getNombre())
                .orElse("Desconocido");
    }

    public List<Mascota> obtenerListaMascotas() {
        return mascotas;
    }
}

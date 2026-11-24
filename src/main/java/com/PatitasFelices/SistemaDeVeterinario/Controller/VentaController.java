package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Venta;
import com.PatitasFelices.SistemaDeVeterinario.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "ventas");
        model.addAttribute("ventas", ventaService.listarRecientes());
        return "admin/ventas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Venta venta = ventaService.buscarPorId(id);
        if (venta.getId() == null) return "redirect:/admin/ventas";   // no existe
        model.addAttribute("paginaActiva", "ventas");
        model.addAttribute("venta", venta);
        return "admin/formulario-venta";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam Long id,
                          @RequestParam int cantidad,
                          @RequestParam String metodoPago,
                          Model model) {
        String error = ventaService.actualizar(id, cantidad, metodoPago);
        if (error != null) {
            Venta venta = ventaService.buscarPorId(id);
            if (venta.getId() == null) return "redirect:/admin/ventas";
            model.addAttribute("paginaActiva", "ventas");
            model.addAttribute("venta", venta);
            model.addAttribute("error", error);
            return "admin/formulario-venta";
        }
        return "redirect:/admin/ventas";
    }


    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return "redirect:/admin/ventas";
    }
}
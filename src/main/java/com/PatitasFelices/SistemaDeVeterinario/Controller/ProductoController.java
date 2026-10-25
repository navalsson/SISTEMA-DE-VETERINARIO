package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Producto;
import com.PatitasFelices.SistemaDeVeterinario.service.ProductoService;
import com.PatitasFelices.SistemaDeVeterinario.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productos")
public class ProductoController {

    @Autowired private ProductoService productoService;
    @Autowired private VentaService ventaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "productos");
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("ventas", ventaService);   // unidades vendidas e ingresos por producto
        return "admin/productos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paginaActiva", "productos");
        model.addAttribute("producto", new Producto());
        return "admin/formulario-producto";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("paginaActiva", "productos");
        model.addAttribute("producto", productoService.buscarPorId(id));
        return "admin/formulario-producto";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {

        Producto existente = productoService.buscarPorNombre(producto.getNombre());
        if (existente != null && !existente.getId().equals(producto.getId())) {
            result.rejectValue("nombre", "duplicado", "Ya existe un producto con ese nombre");
        }
        if (result.hasErrors()) {
            model.addAttribute("paginaActiva", "productos");
            return "admin/formulario-producto";
        }
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return "redirect:/admin/productos";
    }
}
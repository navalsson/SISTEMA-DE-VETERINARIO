package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Categoria;
import com.PatitasFelices.SistemaDeVeterinario.Model.Producto;
import com.PatitasFelices.SistemaDeVeterinario.service.CategoriaService;
import com.PatitasFelices.SistemaDeVeterinario.service.ProductoService;
import com.PatitasFelices.SistemaDeVeterinario.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/productos")
public class ProductoController {

    @Autowired private ProductoService productoService;
    @Autowired private VentaService ventaService;
    @Autowired private CategoriaService categoriaService;


    private void cargarFormulario(Model model) {
        model.addAttribute("paginaActiva", "productos");
        model.addAttribute("categorias", categoriaService.listarPorTipo("Producto"));
    }


    private void asegurarCategoria(Producto p) {
        if (p.getCategoria() == null) p.setCategoria(new Categoria());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "productos");
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("ventas", ventaService);   // unidades vendidas e ingresos por producto
        return "admin/productos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Producto p = new Producto();
        asegurarCategoria(p);
        cargarFormulario(model);
        model.addAttribute("producto", p);
        return "admin/formulario-producto";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Producto p = productoService.buscarPorId(id);
        asegurarCategoria(p);
        cargarFormulario(model);
        model.addAttribute("producto", p);
        return "admin/formulario-producto";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Producto producto, BindingResult result, Model model) {
        asegurarCategoria(producto);

        Producto existente = productoService.buscarPorNombre(producto.getNombre());
        if (existente != null && !existente.getId().equals(producto.getId())) {
            result.rejectValue("nombre", "duplicado", "Ya existe un producto con ese nombre");
        }


        if (producto.getCategoria().getId() == null) {
            result.rejectValue("categoria", "requerido", "Selecciona una categoría");
        } else {
            Categoria c = categoriaService.buscarPorId(producto.getCategoria().getId());
            if (c.getId() == null || !"Producto".equals(c.getTipo())) {
                result.rejectValue("categoria", "invalida", "Selecciona una categoría válida");
            } else {
                producto.setCategoria(c);
            }
        }

        if (result.hasErrors()) {
            cargarFormulario(model);
            return "admin/formulario-producto";
        }
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        String motivo = productoService.motivoNoEliminar(id);
        if (motivo != null) {
            ra.addFlashAttribute("error", motivo);
        } else {
            productoService.eliminar(id);
        }
        return "redirect:/admin/productos";
    }
}
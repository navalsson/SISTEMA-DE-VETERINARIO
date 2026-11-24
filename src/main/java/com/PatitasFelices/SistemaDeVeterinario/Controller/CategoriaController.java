package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Categoria;
import com.PatitasFelices.SistemaDeVeterinario.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "categorias");
        model.addAttribute("categorias", categoriaService.listar());
        return "admin/categorias";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("paginaActiva", "categorias");
        model.addAttribute("categoria", new Categoria());
        return "admin/formulario-categoria";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("paginaActiva", "categorias");
        model.addAttribute("categoria", categoriaService.buscarPorId(id));
        return "admin/formulario-categoria";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Categoria categoria, BindingResult result, Model model) {

        Categoria existente = categoriaService.buscarPorNombreYTipo(categoria.getNombre(), categoria.getTipo());
        if (existente != null && !existente.getId().equals(categoria.getId())) {
            result.rejectValue("nombre", "duplicado", "Ya existe una categoría con ese nombre en ese tipo");
        }
        if (result.hasErrors()) {
            model.addAttribute("paginaActiva", "categorias");
            return "admin/formulario-categoria";
        }
        categoriaService.guardar(categoria);
        return "redirect:/admin/categorias";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        String motivo = categoriaService.motivoNoEliminar(id);
        if (motivo != null) {
            ra.addFlashAttribute("error", motivo);
        } else {
            categoriaService.eliminar(id);
        }
        return "redirect:/admin/categorias";
    }
}
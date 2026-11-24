package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Categoria;
import com.PatitasFelices.SistemaDeVeterinario.Model.Servicio;
import com.PatitasFelices.SistemaDeVeterinario.service.CategoriaService;
import com.PatitasFelices.SistemaDeVeterinario.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/servicios")
public class ServicioController {

    @Autowired private ServicioService servicioService;
    @Autowired private CategoriaService categoriaService;


    private void cargarFormulario(Model model) {
        model.addAttribute("paginaActiva", "servicios");
        model.addAttribute("categorias", categoriaService.listarPorTipo("Servicio"));
    }


    private void asegurarCategoria(Servicio s) {
        if (s.getCategoria() == null) s.setCategoria(new Categoria());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("paginaActiva", "servicios");
        model.addAttribute("servicios", servicioService.listar());
        return "admin/servicios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Servicio s = new Servicio();
        asegurarCategoria(s);
        cargarFormulario(model);
        model.addAttribute("servicio", s);
        return "admin/formulario-servicio";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Servicio s = servicioService.buscarPorId(id);
        asegurarCategoria(s);
        cargarFormulario(model);
        model.addAttribute("servicio", s);
        return "admin/formulario-servicio";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Servicio servicio, BindingResult result, Model model) {
        asegurarCategoria(servicio);

        Servicio existente = servicioService.buscarPorNombre(servicio.getNombre());
        if (existente != null && !existente.getId().equals(servicio.getId())) {
            result.rejectValue("nombre", "duplicado", "Ya existe un servicio con ese nombre");
        }


        if (servicio.getCategoria().getId() == null) {
            result.rejectValue("categoria", "requerido", "Selecciona una categoría");
        } else {
            Categoria c = categoriaService.buscarPorId(servicio.getCategoria().getId());
            if (c.getId() == null || !"Servicio".equals(c.getTipo())) {
                result.rejectValue("categoria", "invalida", "Selecciona una categoría válida");
            } else {
                servicio.setCategoria(c);
            }
        }

        if (result.hasErrors()) {
            cargarFormulario(model);
            return "admin/formulario-servicio";
        }
        servicioService.guardar(servicio);
        return "redirect:/admin/servicios";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        String motivo = servicioService.motivoNoEliminar(id);
        if (motivo != null) {
            ra.addFlashAttribute("error", motivo);
        } else {
            servicioService.eliminar(id);
        }
        return "redirect:/admin/servicios";
    }
}
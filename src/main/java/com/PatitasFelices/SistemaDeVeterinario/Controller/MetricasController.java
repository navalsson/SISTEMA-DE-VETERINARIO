package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.service.MetricasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MetricasController {

    @Autowired private MetricasService metricasService;

    @GetMapping("/admin/metricas")
    public String metricas(Model model) {
        model.addAttribute("paginaActiva", "metricas");
        model.addAttribute("etiquetas", metricasService.etiquetas());
        model.addAttribute("indiceHoy", MetricasService.DIAS_ANTES);
        model.addAttribute("citas", metricasService.citasPorDia());
        model.addAttribute("ingresos", metricasService.ingresosPorDia());
        model.addAttribute("canceladas", metricasService.canceladasPorDia());
        model.addAttribute("completadas", metricasService.completadasPorDia());
        return "admin/metricas";
    }
}
package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Service
public class MetricasService {

    public static final int DIAS_ANTES = 5;
    public static final int DIAS_DESPUES = 4;

    @Autowired private CitaService citaService;
    @Autowired private VentaService ventaService;

    public List<LocalDate> dias() {
        List<LocalDate> dias = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (int i = -DIAS_ANTES; i <= DIAS_DESPUES; i++) dias.add(hoy.plusDays(i));
        return dias;
    }

    public List<String> etiquetas() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM");
        LocalDate hoy = LocalDate.now();
        List<String> etiquetas = new ArrayList<>();
        for (LocalDate d : dias()) {
            etiquetas.add(d.equals(hoy) ? d.format(f) + " (hoy)" : d.format(f));
        }
        return etiquetas;
    }


    public List<Long> citasPorDia() {
        return contar(c -> !"Cancelada".equals(c.getEstado()));
    }


    public List<Double> ingresosPorDia() {
        List<Double> lista = new ArrayList<>();
        for (LocalDate dia : dias()) {
            double citas = citaService.listar().stream()
                    .filter(c -> esDelDia(c, dia))
                    .filter(c -> !"Cancelada".equals(c.getEstado()))
                    .mapToDouble(Cita::getMontoPagado)
                    .sum();
            double farmacia = ventaService.listar().stream()
                    .filter(v -> v.getFecha() != null && v.getFecha().toLocalDate().equals(dia))
                    .mapToDouble(v -> v.getMonto())
                    .sum();
            lista.add(Math.round((citas + farmacia) * 100) / 100.0);
        }
        return lista;
    }


    public List<Long> canceladasPorDia() {
        return contar(c -> "Cancelada".equals(c.getEstado()));
    }


    public List<Long> completadasPorDia() {
        return contar(c -> "Completada".equals(c.getEstado()));
    }

    private List<Long> contar(Predicate<Cita> filtro) {
        List<Long> lista = new ArrayList<>();
        for (LocalDate dia : dias()) {
            lista.add(citaService.listar().stream()
                    .filter(c -> esDelDia(c, dia))
                    .filter(filtro)
                    .count());
        }
        return lista;
    }

    private boolean esDelDia(Cita c, LocalDate dia) {
        return c.getFechaHora() != null && c.getFechaHora().toLocalDate().equals(dia);
    }
}
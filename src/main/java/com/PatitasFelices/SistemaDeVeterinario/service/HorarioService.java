package com.PatitasFelices.SistemaDeVeterinario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
public class HorarioService {

    private static final List<String> PREMIUM = List.of("07:00", "07:30", "08:00", "08:30");
    private static final List<String> REGULARES = List.of(
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "15:00", "15:30", "16:00");

    @Autowired private CitaService citaService;

    public List<String> listarHorarios(boolean vip) {
        List<String> lista = new ArrayList<>();
        if (vip) lista.addAll(PREMIUM);
        lista.addAll(REGULARES);
        return lista;
    }

    public Set<String> ocupados(LocalDate fecha) {
        return citaService.horariosOcupados(fecha);
    }

    public boolean estaLibre(LocalDate fecha, String hora, boolean vip) {
        if (fecha.isBefore(LocalDate.now())) return false;
        if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) return false;
        return listarHorarios(vip).contains(hora) && !ocupados(fecha).contains(hora);
    }
}
package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import org.springframework.stereotype.Service;
import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReglaNegocioService {

    public boolean tieneDescuentoFidelidad(Cliente c) {
        return c.getCitasCompletadas() >= 5;
    }


    public boolean esVip(Cliente c) {
        return c.getGastoTotal() >= 300;
    }


    public boolean tieneServicioGratis(Cliente c) {
        return c.getCitasEsteMes() >= 3;
    }



    public double calcularMonto(Cliente c, double precio, String metodoPago) {
        double monto = precio;
        if (tieneDescuentoFidelidad(c)) monto *= 0.85;
        if ("Tarjeta".equals(metodoPago)) monto *= 0.95;
        return Math.round(monto * 100) / 100.0;
    }


    public boolean tieneBanoGratis(Mascota m) {
        return m != null && m.getEdad() > 4;
    }

    public List<String> beneficios(Cliente c) {
        List<String> lista = new ArrayList<>();
        if (tieneDescuentoFidelidad(c)) lista.add("15% de descuento por fidelidad");
        if (esVip(c)) lista.add("Cliente VIP: horarios exclusivos");
        if (tieneServicioGratis(c)) lista.add("Servicio adicional gratis en tu siguiente cita");
        return lista;
    }
}
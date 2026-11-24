package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import com.PatitasFelices.SistemaDeVeterinario.Model.Servicio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReglaNegocioService {

    public static final int MESES_BENEFICIO_MASCOTA = 5;
    public static final String SERVICIO_BANO = "Baño y Corte de Uñas";


    public boolean tieneDescuentoFidelidad(Cliente c) {
        return c.getCitasCompletadas() >= 5;
    }


    public boolean esVip(Cliente c) {
        return c.getGastoTotal() >= 300;
    }


    public boolean tieneServicioGratis(Cliente c) {
        return c.getCitasEsteMes() >= 3;
    }


    public boolean tieneBanoGratis(Mascota m) {
        return m != null && m.getEdad() > MESES_BENEFICIO_MASCOTA;
    }

    public boolean esBanoGratis(Mascota m, Servicio s) {
        return tieneBanoGratis(m) && SERVICIO_BANO.equalsIgnoreCase(s.getNombre());
    }


    public double calcularMonto(Cliente c, double precio, String metodoPago) {
        double monto = precio;
        if (tieneDescuentoFidelidad(c)) monto *= 0.85;                  // RN-01
        if ("Tarjeta".equals(metodoPago)) monto *= 0.95;                // RN-02
        return Math.round(monto * 100) / 100.0;
    }


    public double calcularMonto(Cliente c, Mascota m, Servicio s, String metodoPago) {
        if (tieneServicioGratis(c)) return 0.0;                         // RN-04
        if (esBanoGratis(m, s)) return 0.0;                             // RN-05
        return calcularMonto(c, s.getPrecio(), metodoPago);             // RN-01 y RN-02
    }

    public List<String> beneficios(Cliente c) {
        List<String> lista = new ArrayList<>();
        if (tieneDescuentoFidelidad(c)) lista.add("15% de descuento por fidelidad");
        if (esVip(c)) lista.add("Cliente VIP: horarios exclusivos");
        if (tieneServicioGratis(c)) lista.add("Tu próxima cita es gratis");
        return lista;
    }
}
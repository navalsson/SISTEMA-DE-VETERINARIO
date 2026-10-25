package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Venta;
import com.PatitasFelices.SistemaDeVeterinario.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Long id) {
        if (id == null) return new Venta();
        return ventaRepository.findById(id).orElse(new Venta());
    }


    public int unidadesVendidas(Long productoId) {
        return listar().stream()
                .filter(v -> v.getProductoId().equals(productoId))
                .mapToInt(Venta::getCantidad)
                .sum();
    }


    public double ingresos(Long productoId) {
        double total = listar().stream()
                .filter(v -> v.getProductoId().equals(productoId))
                .mapToDouble(Venta::getMonto)
                .sum();
        return Math.round(total * 100) / 100.0;
    }

    public Venta agregar(Venta v) {
        return ventaRepository.save(v);
    }
}
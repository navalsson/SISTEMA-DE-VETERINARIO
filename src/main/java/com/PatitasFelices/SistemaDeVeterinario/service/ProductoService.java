package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Producto;
import com.PatitasFelices.SistemaDeVeterinario.repository.ProductoRepository;
import com.PatitasFelices.SistemaDeVeterinario.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        if (id == null) return new Producto();
        return productoRepository.findById(id).orElse(new Producto());
    }

    public Producto guardar(Producto p) {
        return productoRepository.save(p);
    }

    public String motivoNoEliminar(Long id) {
        if (ventaRepository.existsByProductoId(id))
            return "No se puede eliminar el producto: tiene ventas registradas.";
        return null;
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto buscarPorNombre(String nombre) {
        if (nombre == null) return null;
        return productoRepository.findByNombreIgnoreCase(nombre.trim()).orElse(null);
    }
}
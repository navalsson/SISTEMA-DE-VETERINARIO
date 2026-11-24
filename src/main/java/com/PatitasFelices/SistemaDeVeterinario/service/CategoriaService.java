package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Categoria;
import com.PatitasFelices.SistemaDeVeterinario.repository.CategoriaRepository;
import com.PatitasFelices.SistemaDeVeterinario.repository.ProductoRepository;
import com.PatitasFelices.SistemaDeVeterinario.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> listarPorTipo(String tipo) {
        return categoriaRepository.findByTipo(tipo);
    }

    public Categoria buscarPorId(Long id) {
        if (id == null) return new Categoria();
        return categoriaRepository.findById(id).orElse(new Categoria());
    }

    public Categoria guardar(Categoria c) {
        return categoriaRepository.save(c);
    }

    public Categoria buscarPorNombreYTipo(String nombre, String tipo) {
        if (nombre == null || tipo == null) return null;
        return categoriaRepository
                .findByNombreIgnoreCaseAndTipo(nombre.trim(), tipo)
                .orElse(null);
    }

    public String motivoNoEliminar(Long id) {
        if (servicioRepository.existsByCategoriaId(id))
            return "No se puede eliminar la categoría: tiene servicios asociados.";
        if (productoRepository.existsByCategoriaId(id))
            return "No se puede eliminar la categoría: tiene productos asociados.";
        return null;
    }

    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
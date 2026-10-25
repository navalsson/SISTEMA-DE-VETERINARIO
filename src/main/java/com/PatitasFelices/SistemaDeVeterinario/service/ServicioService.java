package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Servicio;
import com.PatitasFelices.SistemaDeVeterinario.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }

    public List<Servicio> listarActivos() {
        return servicioRepository.findByEstado("Activo");
    }

    public Servicio buscarPorId(Long id) {
        if (id == null) return new Servicio();
        return servicioRepository.findById(id).orElse(new Servicio());
    }

    public Servicio guardar(Servicio s) {
        return servicioRepository.save(s);
    }

    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }


    public Servicio buscarPorNombre(String nombre) {
        if (nombre == null) return null;
        return servicioRepository.findByNombreIgnoreCase(nombre.trim()).orElse(null);
    }
}
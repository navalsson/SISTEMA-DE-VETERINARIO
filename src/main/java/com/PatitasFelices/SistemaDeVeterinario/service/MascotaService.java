package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import com.PatitasFelices.SistemaDeVeterinario.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

    public Mascota buscarPorId(Long id) {
        return mascotaRepository.findById(id).orElse(new Mascota());
    }

    public Mascota guardar(Mascota m) {
        return mascotaRepository.save(m);
    }

    public Mascota buscarPorDuenoYNombre(Long clienteId, String nombre) {
        if (clienteId == null || nombre == null) return null;
        return mascotaRepository
                .findByClienteIdAndNombreIgnoreCase(clienteId, nombre.trim())
                .orElse(null);
    }

    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }
}
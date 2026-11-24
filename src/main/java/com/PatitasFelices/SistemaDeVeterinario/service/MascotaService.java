package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import com.PatitasFelices.SistemaDeVeterinario.repository.CitaRepository;
import com.PatitasFelices.SistemaDeVeterinario.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private CitaRepository citaRepository;

    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

    public Mascota buscarPorId(Long id) {
        Mascota vacia = new Mascota();
        vacia.setCliente(new Cliente());
        if (id == null) return vacia;
        return mascotaRepository.findById(id).orElse(vacia);
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


    public String motivoNoEliminar(Long id) {
        if (citaRepository.existsByMascotaId(id))
            return "No se puede eliminar la mascota: tiene citas registradas.";
        return null;
    }

    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }
}
package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CitaService {

    @Autowired private CitaRepository citaRepository;
    @Autowired private ClienteService clienteService;



    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    public Cita buscarPorId(Long id) {
        if (id == null) return new Cita();
        return citaRepository.findById(id).orElse(new Cita());
    }


    public Cita agregar(Cita c) {
        return citaRepository.save(c);
    }


    public Cita guardar(Cita c) {
        if (c.getId() == null) {
            if (c.getEstado() == null || c.getEstado().isBlank()) c.setEstado("Confirmada");
            return citaRepository.save(c);
        }

        Cita anterior = citaRepository.findById(c.getId()).orElse(null);
        if (anterior != null && "Completada".equals(c.getEstado())
                && !"Completada".equals(anterior.getEstado())) {
            sumarCompletada(c.getClienteId());
        }
        return citaRepository.save(c);
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }



    private void sumarCompletada(Long clienteId) {
        Cliente cl = clienteService.buscarPorId(clienteId);
        if (cl.getId() == null) return;
        cl.setCitasCompletadas(cl.getCitasCompletadas() + 1);
        clienteService.guardar(cl);
    }

    public Set<String> horariosOcupados(LocalDate fecha) {
        return listar().stream()
                .filter(c -> !"Cancelada".equals(c.getEstado()))
                .filter(c -> c.getFechaHora().toLocalDate().equals(fecha))
                .map(c -> c.getFechaHora().toLocalTime().toString())
                .collect(Collectors.toSet());
    }
}
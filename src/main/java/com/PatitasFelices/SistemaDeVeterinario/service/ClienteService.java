package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        if (id == null) return new Cliente();
        return clienteRepository.findById(id).orElse(new Cliente());
    }

    public Cliente buscarPorDni(String dni) {
        if (dni == null) return null;
        return clienteRepository.findByDni(dni).orElse(null);
    }

    // C y U: si el id es nulo hace INSERT; si ya existe hace UPDATE
    public Cliente guardar(Cliente c) {
        if (c.getId() == null && c.getFechaRegistro() == null) c.setFechaRegistro(LocalDate.now());
        return clienteRepository.save(c);
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Cuenta;
import com.PatitasFelices.SistemaDeVeterinario.repository.CitaRepository;
import com.PatitasFelices.SistemaDeVeterinario.repository.ClienteRepository;
import com.PatitasFelices.SistemaDeVeterinario.repository.CuentaRepository;
import com.PatitasFelices.SistemaDeVeterinario.repository.MascotaRepository;
import com.PatitasFelices.SistemaDeVeterinario.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired private MascotaRepository mascotaRepository;
    @Autowired private CitaRepository citaRepository;
    @Autowired private VentaRepository ventaRepository;
    @Autowired private CuentaRepository cuentaRepository;

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


    public Cliente buscarPorUsuario(String usuario) {
        if (usuario == null) return null;
        return cuentaRepository.findByUsuario(usuario)
                .map(Cuenta::getCliente)
                .orElse(null);
    }

    @Transactional
    public Cliente guardar(Cliente c) {
        if (c.getId() == null && c.getFechaRegistro() == null) c.setFechaRegistro(LocalDate.now());
        Cliente guardado = clienteRepository.save(c);


        cuentaRepository.findByClienteId(guardado.getId()).ifPresent(cuenta -> {
            if (!cuenta.getUsuario().equals(guardado.getDni())) {
                cuenta.setUsuario(guardado.getDni());
                cuentaRepository.save(cuenta);
            }
        });
        return guardado;
    }

    public String motivoNoEliminar(Long id) {
        if (mascotaRepository.existsByClienteId(id))
            return "No se puede eliminar el cliente: tiene mascotas registradas.";
        if (citaRepository.existsByClienteId(id))
            return "No se puede eliminar el cliente: tiene citas registradas.";
        if (ventaRepository.existsByClienteId(id))
            return "No se puede eliminar el cliente: tiene compras registradas.";
        return null;
    }


    @Transactional
    public void eliminar(Long id) {
        cuentaRepository.findByClienteId(id).ifPresent(cuentaRepository::delete);
        clienteRepository.deleteById(id);
    }
}
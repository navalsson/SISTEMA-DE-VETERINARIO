package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class ReservaService {

    @Autowired private CitaService citaService;
    @Autowired private ClienteService clienteService;
    @Autowired private MascotaService mascotaService;
    @Autowired private ServicioService servicioService;
    @Autowired private HorarioService horarioService;
    @Autowired private ReglaNegocioService reglaService;


    public boolean esVip(String dni) {
        Cliente c = clienteService.buscarPorDni(dni);
        return c != null && reglaService.esVip(c);
    }

    public synchronized Cita reservar(Long servicioId, LocalDate fecha, String hora,
                                      String nombre, String dni, String telefono, String email,
                                      String nombreMascota, String especie, String raza,
                                      int edad, String sexo, Double peso,
                                      String metodoPago) {

        Cliente existente = clienteService.buscarPorDni(dni);
        boolean vip = existente != null && reglaService.esVip(existente);

        if (!horarioService.estaLibre(fecha, hora, vip)) return null;

        Servicio servicio = servicioService.buscarPorId(servicioId);
        if (servicio.getId() == null) return null;

        Cliente cliente;
        if (existente != null) {
            cliente = existente;
            cliente.setTelefono(telefono);
            cliente.setEmail(email);
        } else {
            cliente = clienteService.guardar(
                    new Cliente(null, nombre, dni, telefono, email, 0, 0.0, LocalDate.now(), 0));
        }


        double monto = reglaService.calcularMonto(cliente, servicio.getPrecio(), metodoPago);


        cliente.setCitasEsteMes(cliente.getCitasEsteMes() + 1);
        cliente.setGastoTotal(cliente.getGastoTotal() + monto);
        clienteService.guardar(cliente);

        Mascota mascota = mascotaService.buscarPorDuenoYNombre(cliente.getId(), nombreMascota);
        if (mascota == null) {
            mascota = mascotaService.guardar(
                    new Mascota(null, nombreMascota, especie, raza, edad, sexo,
                            peso != null ? peso : 0.0, cliente.getId()));
        } else {

            mascota.setEdad(edad);
            mascota.setPeso(peso != null ? peso : mascota.getPeso());
            mascotaService.guardar(mascota);
        }

        return citaService.agregar(new Cita(null, cliente.getId(), mascota.getId(), servicioId,
                LocalDateTime.of(fecha, LocalTime.parse(hora)),
                "Confirmada", metodoPago, monto));
    }
}
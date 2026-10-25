package com.PatitasFelices.SistemaDeVeterinario.config;

import com.PatitasFelices.SistemaDeVeterinario.Model.*;
import com.PatitasFelices.SistemaDeVeterinario.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DatosIniciales {


    @Bean
    CommandLineRunner cargarDatos(ClienteRepository clienteRepo,
                                  ServicioRepository servicioRepo,
                                  MascotaRepository mascotaRepo,
                                  ProductoRepository productoRepo,
                                  CitaRepository citaRepo,
                                  AdministradorRepository adminRepo) {
        return args -> {

            if (clienteRepo.count() == 0) {
                clienteRepo.save(new Cliente(null, "Juan Pérez", "45678912", "987654321", "juan@example.com",
                        4, 290.0, LocalDate.of(2025, 3, 10), 2));
                clienteRepo.save(new Cliente(null, "María Torres", "41234567", "945112233", "maria@example.com",
                        1, 80.0, LocalDate.of(2026, 1, 5), 1));
                clienteRepo.save(new Cliente(null, "Lucía Gómez", "48765432", "912334556", "lucia@example.com",
                        2, 120.0, LocalDate.of(2024, 8, 20), 1));
            }

            if (servicioRepo.count() == 0) {
                servicioRepo.save(servicio("Consulta Médica General", "Servicio Médico", 30.00,
                        "Evaluación clínica completa del estado de salud de tu perro o gato."));
                servicioRepo.save(servicio("Vacunación y Refuerzo", "Servicio Médico", 80.00,
                        "Aplicación de vacunas esenciales para prevenir enfermedades infecciosas."));
                servicioRepo.save(servicio("Baño y Corte de Uñas", "Estética", 50.00,
                        "Baño medicado, secado, limpieza de oídos y corte de uñas."));
            }

            if (mascotaRepo.count() == 0) {
                mascotaRepo.save(new Mascota(null, "Firulais", "Perro", "Labrador", 3, "Macho", 28.5, 1L));
                mascotaRepo.save(new Mascota(null, "Michi", "Gato", "Siamés", 2, "Hembra", 4.2, 2L));
                mascotaRepo.save(new Mascota(null, "Toby", "Perro", "Criollo", 4, "Macho", 15.0, 3L));
            }

            if (productoRepo.count() == 0) {
                productoRepo.save(new Producto(null, "Antipulgas Pipeta Perro", "Antipulgas", 35.00, 20));
                productoRepo.save(new Producto(null, "Vitaminas Multivit Gato", "Vitaminas", 28.50, 15));
                productoRepo.save(new Producto(null, "Desparasitante Oral", "Desparasitante", 18.00, 30));
            }


            if (citaRepo.count() == 0) {
                LocalDate hoy = LocalDate.now();
                citaRepo.save(new Cita(null, 1L, 1L, 1L, hoy.minusDays(2).atTime(10, 0), "Completada", "Tarjeta", 28.50));
                citaRepo.save(new Cita(null, 2L, 2L, 2L, hoy.minusDays(1).atTime(11, 0), "Cancelada", "Efectivo", 80.00));
                citaRepo.save(new Cita(null, 3L, 3L, 3L, hoy.atTime(15, 0), "Confirmada", "Efectivo", 50.00));
            }


            if (adminRepo.count() == 0) {
                adminRepo.save(new Administrador(null, "admin@patitasfelices.com", "admin123"));
            }
        };
    }

    private Servicio servicio(String nombre, String categoria, double precio, String descripcion) {
        Servicio s = new Servicio(null, nombre, categoria, precio, "Activo");
        s.setDescripcion(descripcion);
        return s;
    }
}
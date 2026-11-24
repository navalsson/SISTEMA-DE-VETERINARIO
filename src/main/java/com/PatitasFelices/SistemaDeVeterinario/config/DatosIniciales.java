package com.PatitasFelices.SistemaDeVeterinario.config;

import com.PatitasFelices.SistemaDeVeterinario.Model.*;
import com.PatitasFelices.SistemaDeVeterinario.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarDatos(ClienteRepository clienteRepo,
                                  CategoriaRepository categoriaRepo,
                                  ServicioRepository servicioRepo,
                                  MascotaRepository mascotaRepo,
                                  ProductoRepository productoRepo,
                                  CitaRepository citaRepo,
                                  CuentaRepository cuentaRepo,
                                  PasswordEncoder encoder) {
        return args -> {

            if (clienteRepo.count() == 0) {

                clienteRepo.save(new Cliente(null, "Juan Pérez", "45678912", "987654321", "juan@example.com",
                        4, 150.0, LocalDate.of(2025, 3, 10), 0));

                clienteRepo.save(new Cliente(null, "María Torres", "41234567", "945112233", "maria@example.com",
                        1, 290.0, LocalDate.of(2026, 1, 5), 1));

                clienteRepo.save(new Cliente(null, "Lucía Gómez", "48765432", "912334556", "lucia@example.com",
                        2, 120.0, LocalDate.of(2024, 8, 20), 2));
            }


            if (categoriaRepo.count() == 0) {
                categoriaRepo.save(new Categoria(null, "Servicio Médico", "Servicio"));
                categoriaRepo.save(new Categoria(null, "Estética", "Servicio"));
                categoriaRepo.save(new Categoria(null, "Antipulgas", "Producto"));
                categoriaRepo.save(new Categoria(null, "Vitaminas", "Producto"));
                categoriaRepo.save(new Categoria(null, "Desparasitante", "Producto"));
            }

            if (servicioRepo.count() == 0) {
                Categoria medico = categoriaRepo.findByNombreIgnoreCaseAndTipo("Servicio Médico", "Servicio").orElseThrow();
                Categoria estetica = categoriaRepo.findByNombreIgnoreCaseAndTipo("Estética", "Servicio").orElseThrow();

                servicioRepo.save(servicio("Consulta Médica General", medico, 30.00,
                        "Evaluación clínica completa del estado de salud de tu perro o gato."));
                servicioRepo.save(servicio("Vacunación y Refuerzo", medico, 80.00,
                        "Aplicación de vacunas esenciales para prevenir enfermedades infecciosas."));
                servicioRepo.save(servicio("Baño y Corte de Uñas", estetica, 50.00,
                        "Baño medicado, secado, limpieza de oídos y corte de uñas."));
            }

            if (mascotaRepo.count() == 0) {
                List<Cliente> clientes = clienteRepo.findAll();
                mascotaRepo.save(new Mascota(null, "Firulais", "Perro", "Labrador", 4, "Macho", 28.5, clientes.get(0)));
                mascotaRepo.save(new Mascota(null, "Michi", "Gato", "Siamés", 2, "Hembra", 4.2, clientes.get(1)));
                mascotaRepo.save(new Mascota(null, "Toby", "Perro", "Criollo", 1, "Macho", 15.0, clientes.get(2)));
            }

            if (productoRepo.count() == 0) {
                Categoria antipulgas = categoriaRepo.findByNombreIgnoreCaseAndTipo("Antipulgas", "Producto").orElseThrow();
                Categoria vitaminas = categoriaRepo.findByNombreIgnoreCaseAndTipo("Vitaminas", "Producto").orElseThrow();
                Categoria desparasitante = categoriaRepo.findByNombreIgnoreCaseAndTipo("Desparasitante", "Producto").orElseThrow();

                productoRepo.save(new Producto(null, "Antipulgas Pipeta Perro", antipulgas, 35.00, 20));
                productoRepo.save(new Producto(null, "Vitaminas Multivit Gato", vitaminas, 28.50, 15));
                productoRepo.save(new Producto(null, "Desparasitante Oral", desparasitante, 18.00, 30));
            }

            if (citaRepo.count() == 0) {
                LocalDate hoy = LocalDate.now();
                List<Mascota> mascotas = mascotaRepo.findAll();
                List<Servicio> servicios = servicioRepo.findAll();
                citaRepo.save(new Cita(null, mascotas.get(0).getCliente(), mascotas.get(0), servicios.get(0),
                        hoy.minusDays(2).atTime(10, 0), "Completada", "Tarjeta", 28.50));
                citaRepo.save(new Cita(null, mascotas.get(1).getCliente(), mascotas.get(1), servicios.get(1),
                        hoy.minusDays(1).atTime(11, 0), "Cancelada", "Efectivo", 80.00));
                citaRepo.save(new Cita(null, mascotas.get(2).getCliente(), mascotas.get(2), servicios.get(2),
                        hoy.atTime(15, 0), "Confirmada", "Efectivo", 50.00));
            }

            if (cuentaRepo.count() == 0) {


                cuentaRepo.save(new Cuenta(null, "admin",
                        encoder.encode("admin123"), Rol.ADMIN, null));


                Cliente juan = clienteRepo.findByDni("45678912").orElseThrow();
                cuentaRepo.save(new Cuenta(null, "45678912",
                        encoder.encode("cliente123"), Rol.CLIENTE, juan));
            }
        };
    }

    private Servicio servicio(String nombre, Categoria categoria, double precio, String descripcion) {
        Servicio s = new Servicio(null, nombre, categoria, precio, "Activo");
        s.setDescripcion(descripcion);
        return s;
    }
}
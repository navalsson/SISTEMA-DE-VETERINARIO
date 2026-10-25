package com.PatitasFelices.SistemaDeVeterinario.repository;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    Optional<Cliente> findByDni(String dni);
}
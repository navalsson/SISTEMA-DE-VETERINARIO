package com.PatitasFelices.SistemaDeVeterinario.repository;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByUsuario(String usuario);

    Optional<Cuenta> findByClienteId(Long clienteId);

    boolean existsByUsuario(String usuario);

    boolean existsByClienteId(Long clienteId);
}
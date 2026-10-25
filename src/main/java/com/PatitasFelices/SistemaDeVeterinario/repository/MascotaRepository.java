package com.PatitasFelices.SistemaDeVeterinario.repository;

import com.PatitasFelices.SistemaDeVeterinario.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    Optional<Mascota> findByClienteIdAndNombreIgnoreCase(Long clienteId, String nombre);
}
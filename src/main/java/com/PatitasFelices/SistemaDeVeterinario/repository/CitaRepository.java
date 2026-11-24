package com.PatitasFelices.SistemaDeVeterinario.repository;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByClienteId(Long clienteId);

    boolean existsByMascotaId(Long mascotaId);

    boolean existsByServicioId(Long servicioId);
}
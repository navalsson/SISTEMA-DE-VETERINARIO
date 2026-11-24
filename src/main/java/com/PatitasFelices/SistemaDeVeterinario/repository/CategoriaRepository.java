package com.PatitasFelices.SistemaDeVeterinario.repository;

import com.PatitasFelices.SistemaDeVeterinario.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByTipo(String tipo);

    Optional<Categoria> findByNombreIgnoreCaseAndTipo(String nombre, String tipo);
}
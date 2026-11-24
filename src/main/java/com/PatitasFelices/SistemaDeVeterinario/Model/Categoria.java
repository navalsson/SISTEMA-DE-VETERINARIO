package com.PatitasFelices.SistemaDeVeterinario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "categorias",
        uniqueConstraints = @UniqueConstraint(columnNames = {"nombre", "tipo"}))
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 50, message = "El nombre no puede pasar de 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "Selecciona el tipo")
    @Pattern(regexp = "Servicio|Producto", message = "El tipo debe ser Servicio o Producto")
    @Column(nullable = false, length = 20)
    private String tipo;

    public Categoria() {
    }

    public Categoria(Long id, String nombre, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
package com.PatitasFelices.SistemaDeVeterinario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre del servicio es obligatorio")
    @Size(max = 100, message = "El nombre no puede pasar de 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;
    @NotBlank(message = "Selecciona una categoría")
    @Column(nullable = false)
    private String categoria;
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    private double precio;
    @Pattern(regexp = "Activo|Inactivo", message = "Estado no válido")
    @Column(nullable = false)
    private String estado;
    @Size(max = 500, message = "La descripción no puede pasar de 500 caracteres")
    @Column(length = 500)
    private String descripcion;

    public Servicio() {
    }

    public Servicio(Long id, String nombre, String categoria, double precio, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
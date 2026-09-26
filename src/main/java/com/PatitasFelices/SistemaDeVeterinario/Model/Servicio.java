package com.PatitasFelices.SistemaDeVeterinario.Model;

public class Servicio {
    private Long id;
    private String nombre;
    private String categoria;
    private double precio;
    private String estado;

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
}

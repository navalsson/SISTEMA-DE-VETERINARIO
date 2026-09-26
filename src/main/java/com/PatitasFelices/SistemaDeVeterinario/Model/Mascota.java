package com.PatitasFelices.SistemaDeVeterinario.Model;

public class Mascota {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private String sexo;
    private double peso;
    private Long clienteId;

    public Mascota() {
    }

    public Mascota(Long id, String nombre, String especie, String raza,
                   String edad, String sexo, double peso, Long clienteId) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.sexo = sexo;
        this.peso = peso;
        this.clienteId = clienteId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public String getEdad() { return edad; }
    public void setEdad(String edad) { this.edad = edad; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}

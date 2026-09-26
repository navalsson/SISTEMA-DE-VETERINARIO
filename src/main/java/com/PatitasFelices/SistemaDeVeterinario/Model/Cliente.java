package com.PatitasFelices.SistemaDeVeterinario.Model;

import java.time.LocalDate;

public class Cliente {

    private Long id;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private int citasCompletadas;
    private double gastoTotal;
    private LocalDate fechaRegistro;
    private int citasEsteMes;

    public Cliente() {
    }

    public Cliente(Long id, String nombre, String dni, String telefono, String email,
                   int citasCompletadas, double gastoTotal,
                   LocalDate fechaRegistro, int citasEsteMes) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.citasCompletadas = citasCompletadas;
        this.gastoTotal = gastoTotal;
        this.fechaRegistro = fechaRegistro;
        this.citasEsteMes = citasEsteMes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getCitasCompletadas() { return citasCompletadas; }
    public void setCitasCompletadas(int citasCompletadas) { this.citasCompletadas = citasCompletadas; }

    public double getGastoTotal() { return gastoTotal; }
    public void setGastoTotal(double gastoTotal) { this.gastoTotal = gastoTotal; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public int getCitasEsteMes() { return citasEsteMes; }
    public void setCitasEsteMes(int citasEsteMes) { this.citasEsteMes = citasEsteMes; }
}

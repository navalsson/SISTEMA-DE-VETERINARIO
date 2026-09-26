package com.PatitasFelices.SistemaDeVeterinario.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cliente {

    private Long id;
    private String Nombre;
    private String Telefono;
    private String Email;
    private int citasCompletadas;
    private double gastoTotal;
    private LocalDate fechaRegistro;
    private int citasEsteMes;

    public Cliente() {
    }
    public Cliente(Long id, String Nombre, String Telefono,String email, int citasCompletadas, double gastoTotal, LocalDate fechaRegistro, int citasEsteMes) {
        this.id = id;
        this.Nombre = Nombre;
        this.Telefono = Telefono;
        this.Email = email;
        this.citasCompletadas = citasCompletadas;
        this.gastoTotal = gastoTotal;
        this.fechaRegistro = fechaRegistro;
        this.citasEsteMes = citasEsteMes;
    }
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return Nombre;}
    public void setNombre(String Nombre) {this.Nombre = Nombre;}

    public String getTelefono() {return Telefono;}
    public void setTelefono(String Telefono) {this.Telefono = Telefono;}

    public String getEmail() {return Email;}
    public void setEmail(String Email) {this.Email = Email;}

    public int getCitasCompletadas() {return citasCompletadas;}
    public void setCitasCompletadas(int citasCompletadas) {this.citasCompletadas = citasCompletadas;}

    public double getGastoTotal() {return gastoTotal;}
    public void setGastoTotal(double gastoTotal) {this.gastoTotal = gastoTotal;}

    public LocalDate getFechaRegistro() {return fechaRegistro;}
    public void setFechaRegistro(LocalDate fechaRegistro) {this.fechaRegistro = fechaRegistro;}

    public int getCitasEsteMes() {return citasEsteMes;}
    public void setCitasEsteMes(int citasEsteMes) {this.citasEsteMes = citasEsteMes;}
}

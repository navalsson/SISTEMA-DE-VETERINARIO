package com.PatitasFelices.SistemaDeVeterinario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede pasar de 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;
    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
    @Column(nullable = false, unique = true, length = 8)
    private String dni;
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\d{9}", message = "El teléfono debe tener 9 dígitos")
    @Column(nullable = false, length = 9)
    private String telefono;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 100, message = "El email no puede pasar de 100 caracteres")
    @Column(nullable = false, length = 100)
    private String email;
    @Min(value = 0, message = "No puede ser negativo")
    private int citasCompletadas;
    @DecimalMin(value = "0.0", message = "El gasto no puede ser negativo")
    private double gastoTotal;
    private LocalDate fechaRegistro;
    @Min(value = 0, message = "No puede ser negativo")
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

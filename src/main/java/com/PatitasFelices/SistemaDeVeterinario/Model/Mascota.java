package com.PatitasFelices.SistemaDeVeterinario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la mascota es obligatorio")
    @Size(max = 50, message = "El nombre no puede pasar de 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;
    @NotNull(message = "Selecciona la especie")
    @Pattern(regexp = "Perro|Gato", message = "Solo se admiten perros y gatos")
    @Column(nullable = false, length = 20)
    private String especie;
    @Size(max = 50, message = "La raza no puede pasar de 50 caracteres")
    private String raza;
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 360, message = "La edad máxima es 30 años (360 meses)")
    private int edad;
    @Pattern(regexp = "Macho|Hembra", message = "Selecciona Macho o Hembra")
    private String sexo;
    @DecimalMin(value = "0.0", message = "El peso no puede ser negativo")
    @DecimalMax(value = "150.0", message = "El peso máximo es 150 kg")
    private double peso;

    @NotNull(message = "Selecciona el dueño")
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    public Mascota() {
    }

    public Mascota(Long id, String nombre, String especie, String raza,
                   int edad, String sexo, double peso, Long clienteId) {
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

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    // Edad para mostrar: "5 meses", "1 año", "2 años 3 meses"
    public String getEdadTexto() {
        if (edad < 12) return edad + (edad == 1 ? " mes" : " meses");
        int anios = edad / 12, meses = edad % 12;
        String texto = anios + (anios == 1 ? " año" : " años");
        if (meses > 0) texto += " " + meses + (meses == 1 ? " mes" : " meses");
        return texto;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
}
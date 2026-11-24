package com.PatitasFelices.SistemaDeVeterinario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "Selecciona un cliente")
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotNull(message = "Selecciona una mascota")
    @ManyToOne(optional = false)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @NotNull(message = "Selecciona un servicio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @NotNull(message = "Ingresa la fecha y la hora")
    @Column(name = "fecha_hora", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaHora;

    @Pattern(regexp = "Confirmada|Completada|No Show|Cancelada", message = "Estado no válido")
    @Column(nullable = false)
    private String estado;
    @Pattern(regexp = "Efectivo|Tarjeta", message = "El método de pago debe ser Efectivo o Tarjeta")
    private String metodoPago;   // RN-02
    @DecimalMin(value = "0.0", message = "El monto no puede ser negativo")
    private double montoPagado;

    public Cita() {
    }

    public Cita(Long id, Cliente cliente, Mascota mascota, Servicio servicio,
                LocalDateTime fechaHora, String estado, String metodoPago, double montoPagado) {
        this.id = id;
        this.cliente = cliente;
        this.mascota = mascota;
        this.servicio = servicio;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.montoPagado = montoPagado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }

    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public double getMontoPagado() { return montoPagado; }
    public void setMontoPagado(double montoPagado) { this.montoPagado = montoPagado; }
}
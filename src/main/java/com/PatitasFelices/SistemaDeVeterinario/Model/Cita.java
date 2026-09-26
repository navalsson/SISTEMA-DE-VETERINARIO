package com.PatitasFelices.SistemaDeVeterinario.Model;
import java.time.LocalDateTime;

public class Cita {
    private Long id;
    private Long clienteId;
    private Long mascotaId;
    private Long servicioId;
    private LocalDateTime fechaHora;
    private String estado;
    private String metodoPago;   // RN-05
    private double montoPagado;

    public Cita() {
    }

    public Cita(Long id, Long clienteId, Long mascotaId, Long servicioId,
                LocalDateTime fechaHora, String estado, String metodoPago, double montoPagado) {
        this.id = id;
        this.clienteId = clienteId;
        this.mascotaId = mascotaId;
        this.servicioId = servicioId;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.montoPagado = montoPagado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }

    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public double getMontoPagado() { return montoPagado; }
    public void setMontoPagado(double montoPagado) { this.montoPagado = montoPagado; }
}

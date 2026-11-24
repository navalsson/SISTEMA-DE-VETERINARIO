package com.PatitasFelices.SistemaDeVeterinario.Model;
import jakarta.persistence.*;

@Entity
@Table(name = "cuentas")

public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String usuario;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Cuenta() {
    }

    public Cuenta(Long id, String usuario, String password, Rol rol, Cliente cliente) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
        this.cliente = cliente;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}

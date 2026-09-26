package com.PatitasFelices.SistemaDeVeterinario.Model;

public class Administrador {
    private Long id;
    private String usuario;
    private String password;

    public Administrador() {
    }

    public Administrador(Long id, String usuario, String password) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

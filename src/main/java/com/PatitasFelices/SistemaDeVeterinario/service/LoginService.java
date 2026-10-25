package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Administrador;
import com.PatitasFelices.SistemaDeVeterinario.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AdministradorRepository administradorRepository;


    public boolean validar(Administrador a) {
        if (a.getUsuario() == null || a.getPassword() == null) return false;
        return administradorRepository.findByUsuario(a.getUsuario())
                .map(x -> a.getPassword().equals(x.getPassword()))
                .orElse(false);
    }
}
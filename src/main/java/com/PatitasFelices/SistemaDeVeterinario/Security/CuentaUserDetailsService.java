package com.PatitasFelices.SistemaDeVeterinario.Security;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cuenta;
import com.PatitasFelices.SistemaDeVeterinario.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CuentaUserDetailsService implements UserDetailsService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Cuenta c = cuentaRepository.findByUsuario(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("No existe la cuenta: " + usuario));

        return User.withUsername(c.getUsuario())
                .password(c.getPassword())
                .roles(c.getRol().name())
                .build();
    }
}
package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Cuenta;
import com.PatitasFelices.SistemaDeVeterinario.Model.Rol;
import com.PatitasFelices.SistemaDeVeterinario.repository.CuentaRepository;
import com.PatitasFelices.SistemaDeVeterinario.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired private ClienteService clienteService;
    @Autowired private CuentaRepository cuentaRepository;
    @Autowired private PasswordEncoder encoder;

    @GetMapping("/registro")
    public String formulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute Cliente cliente, BindingResult result,
                            @RequestParam String password, Model model) {


        if (cliente.getDni() != null && cuentaRepository.existsByUsuario(cliente.getDni())) {
            result.rejectValue("dni", "duplicado", "Ya existe una cuenta con ese DNI");
        }

        boolean passwordCorta = password == null || password.length() < 6;
        if (passwordCorta) {
            model.addAttribute("errorPassword", "La contraseña debe tener al menos 6 caracteres");
        }

        if (result.hasErrors() || passwordCorta) {
            return "registro";
        }


        Cliente guardado = clienteService.buscarPorDni(cliente.getDni());
        if (guardado == null) {
            cliente.setCitasCompletadas(0);
            cliente.setGastoTotal(0.0);
            cliente.setCitasEsteMes(0);
            guardado = clienteService.guardar(cliente);
        }


        cuentaRepository.save(new Cuenta(null, guardado.getDni(),
                encoder.encode(password), Rol.CLIENTE, guardado));

        return "redirect:/login?registrado";
    }
}
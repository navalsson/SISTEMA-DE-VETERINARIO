package com.PatitasFelices.SistemaDeVeterinario.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {

    @GetMapping("/usuario/faq")
    public String mostrarFAQ() {
        return "usuario/faq";
    }
}
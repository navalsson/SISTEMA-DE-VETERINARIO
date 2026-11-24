package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Venta;
import com.PatitasFelices.SistemaDeVeterinario.service.ClienteService;
import com.PatitasFelices.SistemaDeVeterinario.service.CompraService;
import com.PatitasFelices.SistemaDeVeterinario.service.ProductoService;
import com.PatitasFelices.SistemaDeVeterinario.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario/compra")
public class CompraController {
    @Autowired private ProductoService productoService;
    @Autowired private CompraService compraService;
    @Autowired private VentaService ventaService;
    @Autowired private ClienteService clienteService;


    private Cliente clienteLogueado(Authentication auth) {
        return auth == null ? null : clienteService.buscarPorUsuario(auth.getName());
    }

    @GetMapping("/{id}")
    public String formulario(@PathVariable Long id, Authentication auth, Model model) {
        var producto = productoService.buscarPorId(id);
        if (producto.getId() == null || producto.getStock() <= 0) return "redirect:/usuario/servicios";

        Cliente cliente = clienteLogueado(auth);
        if (cliente == null) return "redirect:/usuario/servicios";

        model.addAttribute("producto", producto);
        model.addAttribute("cliente", cliente);   // datos que se muestran ya completos
        return "usuario/compra";
    }

    @PostMapping("/confirmar")
    public String confirmar(Authentication auth,
                            @RequestParam Long productoId,
                            @RequestParam int cantidad,
                            @RequestParam String telefono,
                            @RequestParam String email,
                            @RequestParam String metodoPago) {


        Cliente cliente = clienteLogueado(auth);
        if (cliente == null) return "redirect:/usuario/servicios";

        Venta venta = compraService.comprar(productoId, cantidad,
                cliente.getNombre(), cliente.getDni(), telefono, email, metodoPago);
        if (venta == null) return "redirect:/usuario/compra/" + productoId + "?error=true";
        return "redirect:/usuario/compra/confirmacion/" + venta.getId();
    }

    @GetMapping("/confirmacion/{id}")
    public String confirmacion(@PathVariable Long id, Model model) {
        Venta venta = ventaService.buscarPorId(id);
        if (venta.getId() == null) return "redirect:/usuario/servicios";
        model.addAttribute("venta", venta);
        return "usuario/compra-confirmada";
    }
}

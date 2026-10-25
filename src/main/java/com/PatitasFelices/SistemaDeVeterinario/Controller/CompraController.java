package com.PatitasFelices.SistemaDeVeterinario.Controller;

import com.PatitasFelices.SistemaDeVeterinario.Model.Venta;
import com.PatitasFelices.SistemaDeVeterinario.service.ClienteService;
import com.PatitasFelices.SistemaDeVeterinario.service.CompraService;
import com.PatitasFelices.SistemaDeVeterinario.service.ProductoService;
import com.PatitasFelices.SistemaDeVeterinario.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario/compra")

public class CompraController {
    @Autowired private ProductoService productoService;
    @Autowired private ClienteService clienteService;
    @Autowired private CompraService compraService;
    @Autowired private VentaService ventaService;


    @GetMapping("/{id}")
    public String formulario(@PathVariable Long id, Model model) {
        var producto = productoService.buscarPorId(id);
        if (producto.getId() == null || producto.getStock() <= 0) return "redirect:/usuario/servicios";
        model.addAttribute("producto", producto);
        return "usuario/compra";
    }


    @PostMapping("/confirmar")
    public String confirmar(@RequestParam Long productoId,
                            @RequestParam int cantidad,
                            @RequestParam String nombre,
                            @RequestParam String dni,
                            @RequestParam String telefono,
                            @RequestParam String email,
                            @RequestParam String metodoPago) {

        Venta venta = compraService.comprar(productoId, cantidad, nombre, dni, telefono, email, metodoPago);
        if (venta == null) return "redirect:/usuario/compra/" + productoId + "?error=true";
        return "redirect:/usuario/compra/confirmacion/" + venta.getId();
    }


    @GetMapping("/confirmacion/{id}")
    public String confirmacion(@PathVariable Long id, Model model) {
        Venta venta = ventaService.buscarPorId(id);
        model.addAttribute("venta", venta);
        model.addAttribute("producto", productoService.buscarPorId(venta.getProductoId()));
        model.addAttribute("cliente", clienteService.buscarPorId(venta.getClienteId()));
        return "usuario/compra-confirmada";
    }
}

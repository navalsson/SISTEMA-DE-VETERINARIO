package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Producto;
import com.PatitasFelices.SistemaDeVeterinario.Model.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class CompraService {

    @Autowired private ProductoService productoService;
    @Autowired private ClienteService clienteService;
    @Autowired private ReglaNegocioService reglaService;
    @Autowired private VentaService ventaService;

    public synchronized Venta comprar(Long productoId, int cantidad, String nombre, String dni,
                                      String telefono, String email, String metodoPago) {

        Producto producto = productoService.buscarPorId(productoId);
        if (producto.getId() == null || cantidad < 1 || cantidad > producto.getStock()) return null;

        Cliente cliente = clienteService.buscarPorDni(dni);
        if (cliente == null) {
            cliente = clienteService.guardar(
                    new Cliente(null, nombre, dni, telefono, email, 0, 0.0, LocalDate.now(), 0));
        } else {
            cliente.setTelefono(telefono);
            cliente.setEmail(email);
        }

        double monto = reglaService.calcularMonto(cliente, producto.getPrecio() * cantidad, metodoPago);
        cliente.setGastoTotal(cliente.getGastoTotal() + monto);
        clienteService.guardar(cliente);   // JPA: guardar el nuevo gasto acumulado


        producto.setStock(producto.getStock() - cantidad);
        productoService.guardar(producto);

        return ventaService.agregar(new Venta(null, producto.getId(), cliente.getId(),
                cantidad, monto, metodoPago, LocalDateTime.now()));
    }
}
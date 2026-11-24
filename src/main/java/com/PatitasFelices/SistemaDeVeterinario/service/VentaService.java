package com.PatitasFelices.SistemaDeVeterinario.service;

import com.PatitasFelices.SistemaDeVeterinario.Model.Cliente;
import com.PatitasFelices.SistemaDeVeterinario.Model.Producto;
import com.PatitasFelices.SistemaDeVeterinario.Model.Venta;
import com.PatitasFelices.SistemaDeVeterinario.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private ProductoService productoService;
    @Autowired private ClienteService clienteService;
    @Autowired private ReglaNegocioService reglaService;

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }


    public List<Venta> listarRecientes() {
        return ventaRepository.findAll(Sort.by(Sort.Direction.DESC, "fecha"));
    }

    public Venta buscarPorId(Long id) {
        if (id == null) return new Venta();
        return ventaRepository.findById(id).orElse(new Venta());
    }

    public int unidadesVendidas(Long productoId) {
        return listar().stream()
                .filter(v -> v.getProducto().getId().equals(productoId))
                .mapToInt(Venta::getCantidad)
                .sum();
    }

    public double ingresos(Long productoId) {
        double total = listar().stream()
                .filter(v -> v.getProducto().getId().equals(productoId))
                .mapToDouble(Venta::getMonto)
                .sum();
        return Math.round(total * 100) / 100.0;
    }

    public Venta agregar(Venta v) {
        return ventaRepository.save(v);
    }


    public String actualizar(Long id, int cantidad, String metodoPago) {
        Venta v = buscarPorId(id);
        if (v.getId() == null) return "La venta no existe.";

        if (cantidad < 1) return "La cantidad debe ser al menos 1.";
        if (!"Efectivo".equals(metodoPago) && !"Tarjeta".equals(metodoPago))
            return "El método de pago debe ser Efectivo o Tarjeta.";

        Producto producto = v.getProducto();
        Cliente cliente = v.getCliente();


        int disponible = producto.getStock() + v.getCantidad();
        if (cantidad > disponible)
            return "Stock insuficiente: para esta venta el máximo es " + disponible + " unidades.";


        double nuevoMonto = reglaService.calcularMonto(cliente, producto.getPrecio() * cantidad, metodoPago);


        double gasto = cliente.getGastoTotal() - v.getMonto() + nuevoMonto;
        cliente.setGastoTotal(Math.max(0, Math.round(gasto * 100) / 100.0));
        clienteService.guardar(cliente);


        producto.setStock(disponible - cantidad);
        productoService.guardar(producto);

        v.setCantidad(cantidad);
        v.setMetodoPago(metodoPago);
        v.setMonto(nuevoMonto);
        ventaRepository.save(v);
        return null;
    }


    public void eliminar(Long id) {
        Venta v = buscarPorId(id);
        if (v.getId() == null) return;

        Producto producto = v.getProducto();
        producto.setStock(producto.getStock() + v.getCantidad());
        productoService.guardar(producto);

        Cliente cliente = v.getCliente();
        double gasto = cliente.getGastoTotal() - v.getMonto();
        cliente.setGastoTotal(Math.max(0, Math.round(gasto * 100) / 100.0));
        clienteService.guardar(cliente);

        ventaRepository.deleteById(id);
    }
}
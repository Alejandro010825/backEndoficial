package org.services;

import org.dao.VendedorDAO;
import org.empleado.modelo.Vendedor;
import java.sql.SQLException;
import java.util.List;

public class VendedorService {

    private final VendedorDAO dao;

    public VendedorService() {
        this.dao = new VendedorDAO();
    }

    public VendedorService(VendedorDAO dao) {
        this.dao = dao;
    }

    public List<Vendedor> listarTodas() throws SQLException {
        return dao.listar();
    }

    public Vendedor obtenerPorId(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }
        return dao.obtenerPorId(id).get();
    }

    public Vendedor crear(Vendedor vendedor) throws SQLException {
        validarVendedor(vendedor);
        return dao.crear(vendedor);
    }

    public boolean actualizar(int id, Vendedor vendedor) throws SQLException {
        if (id <= 0 || vendedor == null) {
            return false;
        }
        validarVendedor(vendedor);
        return dao.actualizar(id, vendedor);
    }

    public boolean eliminar(int id) throws SQLException {
        if (id <= 0) {
            return false;
        }
        return dao.eliminar(id);
    }

    private void validarVendedor(Vendedor vendedor) {
        if (vendedor == null) {
            throw new IllegalArgumentException("Vendedor no puede ser null");
        }
    }
}
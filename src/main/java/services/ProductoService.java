package services;

import models.Productos;

import java.util.List;

public interface ProductoService {
    //Implementamos un metodo para listar los productos
    List<Productos> listar();
}

package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Productos;
import services.ProductoService;
import services.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson;  // Asegúrate de tener la librería Gson en tu proyecto

@WebServlet("/productos")
public class ProductosSevlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Verificar si la ruta es para descargar JSON
        if (req.getRequestURI().endsWith("/json")) {
            // Llamar al metodo para enviar como json
            doJson(req, resp);
        } else {
            // Llamar al metodo para mostrar en html
            mostrarProductosHTML(req, resp);
        }
    }


    private void mostrarProductosHTML(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoService service = new ProductoServiceImplement();
        List<Productos> productos = service.listar();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        // Crear la plantilla HTML
        out.print("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Listar Producto</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Listar productos</h1>");

        // Enlace para descargar los productos como JSON
        out.println("<a href=\"/productos/json\" download=\"productos.json\">Descargar Productos en formato JSON</a>");

        // Mostrar tabla de productos
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID PRODUCTO</th>");
        out.println("<th>NOMBRE</th>");
        out.println("<th>CATEGORIA</th>");
        out.println("<th>PRECIO</th>");
        out.println("</tr>");

        productos.forEach(p -> {
            out.println("<tr>");
            out.println("<td>" + p.getIdProducto() + "</td>");
            out.println("<td>" + p.getNombreProducto() + "</td>");
            out.println("<td>" + p.getCategoria() + "</td>");
            out.println("<td>" + p.getPrecioProducto() + "</td>");
            out.println("</tr>");
        });

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    // Metodo para manejar el formato json
    private void doJson(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoService service = new ProductoServiceImplement();
        List<Productos> productos = service.listar();

        // Convertir la lista de productos a JSON utilizando Gson
        Gson gson = new Gson();
        String json = gson.toJson(productos);

        // Configuración de respuesta para descarga
        resp.setContentType("application/json");
        resp.setHeader("Content-Disposition", "attachment; filename=productos.json");
        resp.getWriter().write(json);
    }
}

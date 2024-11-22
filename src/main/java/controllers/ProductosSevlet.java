package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import models.Productos;
import services.ProductoService;
import services.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/productos")
public class ProductosSevlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoService service = new ProductoServiceImplement();
        List<Productos> productos = service.listar();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // Página HTML con Font Awesome y estilos
        out.print("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>Carrito de Compras</title>");

        // Font Awesome para íconos
        out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\">");

        // Estilos CSS
        out.println("<style>");
        out.println("body {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("    background-color: #f4f4f9;");
        out.println("    margin: 0;");
        out.println("    padding: 20px;");
        out.println("}");
        out.println("h1 {");
        out.println("    color: #4A4A55;");
        out.println("    text-align: center;");
        out.println("    font-size: 2.5rem;");
        out.println("    animation: fadeIn 2s ease-in-out;");
        out.println("}");
        out.println("@keyframes fadeIn {");
        out.println("    from { opacity: 0; transform: translateY(-20px); }");
        out.println("    to { opacity: 1; transform: translateY(0); }");
        out.println("}");
        out.println("table {");
        out.println("    width: 80%;");
        out.println("    margin: 20px auto;");
        out.println("    border-collapse: collapse;");
        out.println("    background-color: #fff;");
        out.println("    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);");
        out.println("}");
        out.println("table th, table td {");
        out.println("    padding: 12px;");
        out.println("    text-align: center;");
        out.println("    border: 1px solid #ddd;");
        out.println("}");
        out.println("table th {");
        out.println("    background-color: #6c757d;");
        out.println("    color: #fff;");
        out.println("    font-weight: bold;");
        out.println("}");
        out.println("button {");
        out.println("    display: block;");
        out.println("    margin: 20px auto;");
        out.println("    padding: 10px 20px;");
        out.println("    background-color: #4A4A55;");
        out.println("    color: #fff;");
        out.println("    border: none;");
        out.println("    border-radius: 5px;");
        out.println("    font-size: 1rem;");
        out.println("    cursor: pointer;");
        out.println("    transition: background-color 0.3s ease;");
        out.println("}");
        out.println("button:hover {");
        out.println("    background-color: #343a40;");
        out.println("}");
        out.println("#resultado {");
        out.println("    margin-top: 20px;");
        out.println("    padding: 15px;");
        out.println("    background-color: #fff;");
        out.println("    border: 1px solid #ddd;");
        out.println("    border-radius: 5px;");
        out.println("    width: 80%;");
        out.println("    margin: 20px auto;");
        out.println("    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);");
        out.println("    font-size: 1.2rem;");
        out.println("}");
        out.println(".icono-carrito {");
        out.println("    font-size: 1.2rem;");
        out.println("    color: #4A4A55;");
        out.println("    margin-left: 10px;");
        out.println("    vertical-align: middle;");
        out.println("}");
        out.println("</style>");

        // JavaScript para cálculo
        out.println("<script>");
        out.println("function calcularTotal() {");
        out.println("    let checkboxes = document.querySelectorAll('input[name=\"productoId\"]:checked');");
        out.println("    let subtotal = 0;");
        out.println("    checkboxes.forEach(checkbox => {");
        out.println("        subtotal += parseFloat(checkbox.dataset.precio);");
        out.println("    });");
        out.println("    let iva = subtotal * 0.16;");
        out.println("    let total = subtotal + iva;");
        out.println("    document.getElementById('resultado').innerHTML = `<p><strong>Subtotal:</strong> $${subtotal.toFixed(2)}</p>` +");
        out.println("                                                      `<p><strong>IVA (16%):</strong> $${iva.toFixed(2)}</p>` +");
        out.println("                                                      `<p><strong>Total:</strong> $${total.toFixed(2)}</p>`;");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Lista de Productos</h1>");
        out.println("<table>");
        out.println("<tr>");
        out.println("<th>ID PRODUCTO</th>");
        out.println("<th>NOMBRE</th>");
        out.println("<th>CATEGORIA</th>");
        out.println("<th>PRECIO</th>");
        out.println("<th>AGREGAR</th>");
        out.println("</tr>");

        // Fila con ícono de carrito y checkbox
        productos.forEach(p -> {
            out.println("<tr>");
            out.println("<td>" + p.getIdProducto() + "</td>");
            out.println("<td>" + p.getNombreProducto() + "</td>");
            out.println("<td>" + p.getCategoria() + "</td>");
            out.println("<td>$" + p.getPrecioProducto() + "</td>");
            out.println("<td>");
            out.println("<input type='checkbox' name='productoId' data-precio='" + p.getPrecioProducto() + "'>");
            out.println("<i class='fas fa-shopping-cart icono-carrito'></i>");
            out.println("</td>");
            out.println("</tr>");
        });

        out.println("</table>");
        out.println("<button type='button' onclick='calcularTotal()'>Calcular Total</button>");

        // Contenedor para los resultados
        out.println("<div id='resultado'>");
        out.println("<p>Selecciona productos para ver el total.</p>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}

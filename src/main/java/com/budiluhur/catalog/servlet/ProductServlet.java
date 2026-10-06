package com.budiluhur.catalog.servlet;

import com.budiluhur.catalog.exception.ProductNotFoundException;
import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.repository.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() {
        productRepository = new ProductRepository();

        productRepository.addProduct(
            new Product("PRD-01", "Keyboard Mechanical", 450000.0)
        );

        productRepository.addProduct(
            new Product("PRD-02", "Mouse Wireless Silent", 175000.0)
        );

        productRepository.addProduct(
            new Product("PRD-03", "Monitor Gaming 24 Inch", 2100000.0)
        );
    }

    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // Langkah 1: ambil query parameter "id"
        String id = request.getParameter("id");

        // Langkah 2 dan 3: jika ada ?id=..., tampilkan detail satu produk
        if (id != null && !id.isBlank()) {
            try {
                Product p = productRepository.findById(id);

                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head><title>Detail Produk</title></head>");
                out.println("<body>");
                out.println("<h2>=== DETAIL PRODUK ===</h2>");
                out.println("<p>ID Produk    : " + p.getId() + "</p>");
                out.println("<p>Nama Produk  : " + p.getName() + "</p>");
                out.println("<p>Harga Satuan : Rp" + p.getPrice() + "</p>");
                out.println("<a href='/products'>&larr; Kembali ke daftar</a>");
                out.println("</body>");
                out.println("</html>");

            } catch (ProductNotFoundException e) {
                // Status harus di-set SEBELUM getWriter() / println
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head><title>Error 404</title></head>");
                out.println("<body>");
                out.println("<h2 style='color:red;'>Error 404 - Not Found</h2>");
                out.println("<p style='color:red;'>" + e.getMessage() + "</p>");
                out.println("<a href='/products'>&larr; Kembali ke daftar</a>");
                out.println("</body>");
                out.println("</html>");
            }
            return; // selesai, jangan lanjut ke tabel semua produk
        }

        // Tanpa parameter id: tampilkan seluruh daftar produk
        PrintWriter out = response.getWriter();

        List<Product> products = productRepository.findAll();

        out.println("<html>");
        out.println("<head><title>Katalog Produk Web</title></head>");
        out.println("<body>");

        out.println("<h2>=== DAFTAR KATALOG PRODUK (WEB) ===</h2>");

        out.println("<table border='1' cellpadding='8'>");

        out.println(
            "<tr>" +
            "<th>ID Produk</th>" +
            "<th>Nama Produk</th>" +
            "<th>Harga Satuan</th>" +
            "</tr>"
        );

        for (Product p : products) {
            out.println("<tr>");
            out.println("<td>" + p.getId() + "</td>");
            out.println("<td>" + p.getName() + "</td>");
            out.println("<td>Rp" + p.getPrice() + "</td>");
            out.println("</tr>");
        }

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
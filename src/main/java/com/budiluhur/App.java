package com.budiluhur;

import com.budiluhur.catalog.servlet.CategoryServlet;
import com.budiluhur.catalog.servlet.ProductServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import java.io.File;

public class App {

    public static void main(String[] args) throws Exception {

        System.out.println("==========================================");
        System.out.println("   SISTEM KATALOG PRODUK ENTERPRISE v1.0   ");
        System.out.println("==========================================");

        // Deklarasi variabel produk
        String productName = "Mouse Wireless Silent";
        double unitPrice = 175000.0;
        int stockQuantity = 12;

        // Memanggil method untuk menampilkan informasi
        printProductDetails(productName, unitPrice, stockQuantity);

        // Memeriksa status ketersediaan
        boolean isAvailable = checkStockStatus(stockQuantity);
        System.out.println("Status Ketersediaan: "
                + (isAvailable ? "Tersedia" : "Stok Habis"));

        System.out.println();
        System.out.println("Menjalankan Embedded Tomcat Server...");

        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);

        tomcat.getConnector();

        Context ctx = tomcat.addContext(
                "",
                new File(".").getAbsolutePath()
        );

        // Servlet produk -> /products
        Tomcat.addServlet(
                ctx,
                "ProductServlet",
                new ProductServlet()
        );

        ctx.addServletMappingDecoded(
                "/products",
                "ProductServlet"
        );

        // Servlet kategori -> /categories (tugas Assignment)
        Tomcat.addServlet(
                ctx,
                "CategoryServlet",
                new CategoryServlet()
        );

        ctx.addServletMappingDecoded(
                "/categories",
                "CategoryServlet"
        );

        System.out.println(
                "Server berhasil berjalan di: "
                + "http://localhost:8080/products"
        );
        System.out.println(
                "Kategori: "
                + "http://localhost:8080/categories"
        );

        tomcat.start();

        tomcat.getServer().await();
    }

    public static void printProductDetails(
            String name,
            double price,
            int stock) {

        System.out.println("Nama Produk : " + name);
        System.out.println("Harga Satuan: Rp" + price);
        System.out.println("Jumlah Stok : " + stock + " unit");
    }

    public static boolean checkStockStatus(int stock) {
        return stock > 0;
    }
}
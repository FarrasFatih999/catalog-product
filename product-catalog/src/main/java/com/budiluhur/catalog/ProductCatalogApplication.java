package com.budiluhur.catalog;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.service.ProductService;

@SpringBootApplication
public class ProductCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogApplication.class, args);
    }

    private static String rupiah(double value) {
        return "Rp" + String.format(Locale.of("id", "ID"), "%,.2f", value);
    }

    // Runner sederhana untuk menguji Injection
    @Bean
    public CommandLineRunner run(ProductService productService) {
        return args -> {
            System.out.println("=== SPRING BOOT PRODUCT CATALOG IS RUNNING ===");

            productService.createProduct(
                    new Product("PRD-03", "Monitor Gaming 24 Inch", 2100000.0));

            System.out.println("\nDaftar Produk di Spring Container:");
            productService.getAllProducts().forEach(System.out::println);

            System.out.println("\n--- INFORMASI HARGA & DISKON ---");
            showPrice(productService, "PRD-01", 10.0);
            showPrice(productService, "PRD-02", 0.0);
        };
    }

    private void showPrice(ProductService service, String id, double discount) throws Exception {
        Product p = service.getProductById(id);
        double finalPrice = service.getDiscountedProductPrice(id, discount);

        System.out.println("[" + p.getId() + "] " + p.getName());
        System.out.println("- Harga Normal : " + rupiah(p.getPrice()));
        System.out.println("- Diskon       : " + discount + "% "
                + (discount > 0 ? "(Ada Diskon)" : "(Tidak Ada Diskon)"));
        System.out.println("- Harga Akhir  : " + rupiah(finalPrice) + "\n");
    }
}
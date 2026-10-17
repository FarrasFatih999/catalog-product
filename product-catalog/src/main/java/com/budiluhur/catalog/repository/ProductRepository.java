package com.budiluhur.catalog.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.budiluhur.catalog.exception.ProductNotFoundException;
import com.budiluhur.catalog.model.Product;

@Repository
public class ProductRepository {
    private final List<Product> productList = new ArrayList<>();

    public ProductRepository() {
        // Data awal in-memory
        productList.add(new Product("PRD-01", "Keyboard Mechanical", 450000.0));
        productList.add(new Product("PRD-02", "Mouse Wireless Silent", 175000.0));
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> findAll() {
        return productList;
    }

    public Product findById(String id) throws ProductNotFoundException {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        throw new ProductNotFoundException("Produk dengan ID '" + id + "' tidak ditemukan!");
    }

    public List<Product> searchByName(String keyword) {
    List<Product> result = new ArrayList<>();
    for (Product p : productList) {
        if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
            result.add(p);
        }
    }
    return result;
    }
    
    public boolean deleteById(String id) throws ProductNotFoundException {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                productList.remove(p);
                return true;
            }
        }
        throw new ProductNotFoundException("Produk dengan ID '" + id + "' tidak ditemukan, tidak bisa dihapus!");
    }

        public Product updateProduct(String id, Product updated) throws ProductNotFoundException {
        for (int i = 0; i < productList.size(); i++) {
            Product current = productList.get(i);
            if (current.getId().equalsIgnoreCase(id)) {
                // ID tetap dari URL, hanya nama dan harga yang diganti
                Product result = new Product(current.getId(), updated.getName(), updated.getPrice());
                productList.set(i, result);
                return result;
            }
        }
        throw new ProductNotFoundException("Produk dengan ID '" + id + "' tidak ditemukan, tidak bisa diubah!");
    }
}
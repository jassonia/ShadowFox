package com.ecommerce.product.service;

import com.ecommerce.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public ProductService() {

        products.add(new Product(1, "Laptop", 50000));
        products.add(new Product(2, "Phone", 25000));
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
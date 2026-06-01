package com.ecommerce.product.controller;

import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> getAllProducts() {

        return repository.findAll();
    }

    @PostMapping
    public Product addProduct(
            @RequestBody Product product) {

        return repository.save(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(
            @PathVariable int id) {

        return repository.findById(id)
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable int id,
            @RequestBody Product updatedProduct) {

        updatedProduct.setId(id);

        return repository.save(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable int id) {

        repository.deleteById(id);
    }
}
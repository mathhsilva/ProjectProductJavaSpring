package com.productApp.controller;

import com.productApp.entity.Product;
import com.productApp.entity.ProductQuantity;
import com.productApp.entity.ProductSale;
import com.productApp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<List<Product>> createProduct(@RequestBody @Valid List<Product> product) {
        List<Product> savedProducts = productService.createProduct(product);
        return ResponseEntity.ok(savedProducts);
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellProduct(@RequestBody ProductSale productSale) {
        return productService.sellProduct(productSale);
    }

    @GetMapping
    List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    Product getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @PutMapping
    Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @PutMapping("/add")
    public ResponseEntity<?> updateQuantity(@Valid @RequestBody ProductQuantity request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        try {
            return ResponseEntity.ok(productService.updateQuantity(request.getProductId(), request.getQuantity()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        ResponseEntity<Void> responseEntity = productService.removeProduct(id);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a exclusão for bem-sucedida
        } else {
            return responseEntity; // Retorna o status de erro do serviço
        }
    }
}

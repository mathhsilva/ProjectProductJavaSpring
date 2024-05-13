package com.productApp.service;

import com.productApp.entity.Product;
import com.productApp.entity.ProductSale;
import com.productApp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> createProduct(List<Product> products) {
        return productRepository.saveAll(products);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getById(Integer id) {
      Optional<Product> productOptional =  productRepository.findById(id);
        return productOptional.orElseThrow(()-> new NoSuchElementException("Product not found"));
    }

    public Product updateProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public ResponseEntity<Void> removeProduct(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build(); // Retorna 200 OK
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se o produto não existir
        }
    }

    public Product updateQuantity(Integer productId, int additionalQuantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            int updatedQuantity = product.getQuantity() + additionalQuantity;
            product.setQuantity(updatedQuantity);
            productRepository.save(product);
            return product;
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    @Transactional
    public ResponseEntity<String> sellProduct(ProductSale productSale) {
        Optional<Product> productOptional = productRepository.findById(productSale.getProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getQuantity() >= productSale.getQuantity()) {
                int updatedQuantity = product.getQuantity() - productSale.getQuantity();
                product.setQuantity(updatedQuantity);
                productRepository.save(product);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.badRequest().body("Quantity invalid!");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.productApp.configuration;

import com.productApp.entity.Product;
import com.productApp.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class ProductConfig {

    @Bean
    public ProductRepository productRepositoryBean(ProductRepository productRepository) {
        // Criando a lista de produtos
        List<Product> products = Arrays.asList(
                new Product("Laptop", "Laptop de alta performance", new BigDecimal("2500.00"), 10),
                new Product("Smartphone", "Smartphone com câmera de alta resolução", new BigDecimal("1200.00"), 15),
                new Product("Smart TV", "TV inteligente com resolução 4K", new BigDecimal("1800.00"), 5),
                new Product("Fone de Ouvido", "Fone de ouvido sem fio com cancelamento de ruído", new BigDecimal("300.00"), 20),
                new Product("Tablet", "Tablet com tela retina", new BigDecimal("800.00"), 8)
        );

        // Salvando os produtos na base de dados
        productRepository.saveAll(products);

        // Retornando o ProductRepository
        return productRepository;
    }
}

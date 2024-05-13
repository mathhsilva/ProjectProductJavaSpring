package com.productApp;

import com.productApp.entity.Product;
import com.productApp.repository.ProductRepository;
import com.productApp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductAppTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@MockBean
	private ProductService productService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		Product existingProduct = new Product();
		existingProduct.setId(1);
		existingProduct.setName("Existing Product");
		existingProduct.setDescription("Existing Description");
		existingProduct.setPrice(BigDecimal.valueOf(20.00));
		existingProduct.setQuantity(5);

		// Configure o retorno para chamadas específicas
		when(productService.getById(1)).thenReturn(existingProduct);
		when(productService.getById(50)).thenReturn(null);
	}

	@Test
	void testCreateProduct() throws Exception {
		List<Product> products = new ArrayList<>();
		Product product = new Product("Test Product", "Test Description", BigDecimal.TEN, 10);
		products.add(product);

		when(productService.createProduct(any(List.class))).thenReturn(products);

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content("[{\"name\":\"Test Product\",\"description\":\"Test Description\",\"price\":10,\"quantity\":10}]"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Product"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Test Description"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(10))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].quantity").value(10));

		verify(productService, times(1)).createProduct(any(List.class));
	}

	@Test
	void testCreateProductInvalidData() throws Exception {
		String invalidProductJson = "[{\"description\":\"Test Description\",\"price\":10,\"quantity\":10}]";

		mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(invalidProductJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testProduct() {
		Product product = productService.getById(1);
		assertNotNull(product, "O produto não deveria ser null");
		assertEquals(1, product.getId(), "O produto esperado é o 1");
	}

	@Test
	void testProductNull() {
		Product product = productService.getById(50);
		assertNull(product, "O produto deveria ser null");
	}

	@Test
	void testProductRemove() {
		int productIdToRemove = 1;
		productService.removeProduct(productIdToRemove);
		verify(productService, times(1)).removeProduct(productIdToRemove);
	}

	@Test
	void testProductUpdate() {
		Product productToUpdate = new Product();
		productToUpdate.setId(1); // Definindo o ID para simular uma atualização real
		productService.updateProduct(productToUpdate);
		verify(productService, times(1)).updateProduct(productToUpdate);
	}

	@Test
	void testProductUpdateQuantity() {
//		Product productToUpdateQuantity = new Product();
//		productToUpdateQuantity.setId(1);
//		productToUpdateQuantity.setQuantity(1);
//		productService.updateQuantity(1,2);
//		verify(productService, times(1)).updateQuantity(1,2);
//		productService.updateQuantity(1, 2);

		// Recupera o produto atualizado para verificar a nova quantidade
		Product updatedProduct = productRepository.findById(1).orElseThrow();
		productService.updateQuantity(1, 2);
		assertEquals(10, updatedProduct.getQuantity());
	}
}

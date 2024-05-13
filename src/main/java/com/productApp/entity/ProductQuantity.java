package com.productApp.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQuantity {
    @NotNull(message = "Product id is required")
    private Integer productId;
    @Min(value = 0, message="Qauantity must be a positive integer value")
    private int quantity;
}

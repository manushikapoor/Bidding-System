package com.manushi.product.model.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductUpdateRequestVO {

	@NotBlank(message = "name is required")
	@JsonProperty("name")
	private String name;

	@NotNull(message = "basePrice is required")
	@JsonProperty("base_price")
	private BigDecimal basePrice;

}

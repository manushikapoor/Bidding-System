package com.manushi.product.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestVO {

	@NotBlank(message = "username is required")
	@JsonProperty("username")
	private String username;

	@NotBlank(message = "category is required")
	@JsonProperty("category")
	private String category;

	@NotBlank(message = "name is required")
	@JsonProperty("name")
	private String name;

	@NotNull(message = "basePrice is required")
	@JsonProperty("base_price")
	private BigDecimal basePrice;

	@NotNull(message = "bidStartTime is required")
	@JsonProperty("bid_start_time")
	private LocalDateTime bidStartTime;

	@NotNull(message = "bidEndTime is required")
	@JsonProperty("bid_end_time")
	private LocalDateTime bidEndTime;

}

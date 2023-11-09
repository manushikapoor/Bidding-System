package com.manushi.product.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestVO {

	@NotBlank(message = "name is required")
	@JsonProperty("name")
	private String name;

}

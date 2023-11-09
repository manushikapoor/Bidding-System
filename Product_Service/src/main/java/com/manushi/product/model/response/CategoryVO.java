package com.manushi.product.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonTypeName("categories")
public class CategoryVO {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;

}

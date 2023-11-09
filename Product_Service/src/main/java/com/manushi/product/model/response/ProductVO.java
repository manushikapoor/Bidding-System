package com.manushi.product.model.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("products")
public class ProductVO {

	@JsonProperty("name")
	private String name;

	@JsonProperty("vendor")
	private String vendor;

	@JsonProperty("category")
	private String category;

	@JsonProperty("base_price")
	private String basePrice;

	@JsonProperty("bid_start_time")
	private LocalDateTime bidStartTime;

	@JsonProperty("bid_end_time")
	private LocalDateTime bidEndTime;

	@JsonProperty("listing_date")
	private LocalDateTime listingDate;

}

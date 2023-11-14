package com.manushi.product.model.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.manushi.product.repository.datetime.convertor.LocalDateTimeConverter;

import jakarta.persistence.Convert;
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

	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("status")
	private String status;

	@JsonProperty("vendor")
	private String vendor;

	@JsonProperty("category")
	private String category;

	@JsonProperty("base_price")
	private String basePrice;

	@JsonProperty("bid_start_time")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime bidStartTime;

	@JsonProperty("bid_end_time")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime bidEndTime;

	@JsonProperty("listing_date")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime listingDate;

}

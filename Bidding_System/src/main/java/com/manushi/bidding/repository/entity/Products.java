package com.manushi.bidding.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Products {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@Column(name = "product_id", nullable = false)
	private Long id;

	@NotNull
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "category_id")
	@NotNull
	private Long categoryId;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "base_price")
	private BigDecimal basePrice;

	@NotNull
	@Column(name = "bid_start_time")
	private LocalDateTime bidStartTime;

	@NotNull
	@Column(name = "bid_end_time")
	private LocalDateTime bidEndTime;

	@NotNull
	@Column(name = "status")
	private String status;

	@NotNull
	@Column(name = "listing_date")
	private LocalDateTime listingDate;

}

package com.manushi.product.repository.entity;

import java.math.BigDecimal;

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
@Table(name = "auctions")
public class Auctions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@Column(name = "auction_id", nullable = false)
	private Long id;

	@NotNull
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "user_id")
	@NotNull
	private Long userId;

	@NotNull
	@Column(name = "curr_highest_bid")
	private BigDecimal currHighestBid;

}

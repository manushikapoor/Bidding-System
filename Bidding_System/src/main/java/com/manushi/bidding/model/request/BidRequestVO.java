package com.manushi.bidding.model.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BidRequestVO {

	@NotNull(message = "product is required")
	private Long productId;

	@NotNull(message = "user is required")
	private Long userId;

	@NotNull(message = "amount is required")
	private BigDecimal amount;
}

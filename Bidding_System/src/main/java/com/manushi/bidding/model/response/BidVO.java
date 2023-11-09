package com.manushi.bidding.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidVO {
	private String username;
	private String productName;
	private Long producrId;
	private String amount;
	private LocalDateTime bidCreationTime;
}
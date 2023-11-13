package com.manushi.bidding.util;

import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_BID_AMOUNT;
import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_PRODUCT_NOT_ACTIVE;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.manushi.bidding.exceptions.InvalidRequestException;

@Component
public class RequestValidator {

	public void validateBidRequestVO(BigDecimal amount, LocalDateTime startDateTime, LocalDateTime endDateTime, BigDecimal basePrice) {
		if (!startDateTime.isBefore(LocalDateTime.now()) || !endDateTime.isAfter(LocalDateTime.now())) {
			throw new InvalidRequestException(ERROR_MESSAGE_PRODUCT_NOT_ACTIVE);
		}
		if (amount.compareTo(basePrice) < 0) {
			throw new InvalidRequestException(ERROR_MESSAGE_BID_AMOUNT);
		}
	}

}

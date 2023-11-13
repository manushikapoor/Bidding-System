package com.manushi.bidding.util;

import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_BID_AMOUNT;
import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_PRODUCT_NOT_ACTIVE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.manushi.bidding.exceptions.InvalidRequestException;

@ExtendWith(MockitoExtension.class)
public class RequestValidatorTest {

	@InjectMocks
	private RequestValidator requestValidator;

	@Test
	public void testValidateBidRequestVOValid() {
		BigDecimal amount = BigDecimal.valueOf(200.0);
		LocalDateTime startDateTime = LocalDateTime.now().minusHours(1);
		LocalDateTime endDateTime = LocalDateTime.now().plusHours(1);
		BigDecimal basePrice = BigDecimal.valueOf(150.0);

		assertDoesNotThrow(() -> requestValidator.validateBidRequestVO(amount, startDateTime, endDateTime, basePrice));
	}

	@Test
	public void testValidateBidRequestVOExpiredProduct() {
		BigDecimal amount = BigDecimal.valueOf(200.0);
		LocalDateTime startDateTime = LocalDateTime.now().minusHours(2);
		LocalDateTime endDateTime = LocalDateTime.now().minusHours(1);
		BigDecimal basePrice = BigDecimal.valueOf(150.0);

		InvalidRequestException exception = assertThrows(InvalidRequestException.class,
				() -> requestValidator.validateBidRequestVO(amount, startDateTime, endDateTime, basePrice));
		assertEquals(ERROR_MESSAGE_PRODUCT_NOT_ACTIVE, exception.getMessage());
	}

	@Test
	public void testValidateBidRequestVOLowBidAmount() {
		BigDecimal amount = BigDecimal.valueOf(100.0);
		LocalDateTime startDateTime = LocalDateTime.now().minusHours(1);
		LocalDateTime endDateTime = LocalDateTime.now().plusHours(2);
		BigDecimal basePrice = BigDecimal.valueOf(150.0);

		InvalidRequestException exception = assertThrows(InvalidRequestException.class,
				() -> requestValidator.validateBidRequestVO(amount, startDateTime, endDateTime, basePrice));
		assertEquals(ERROR_MESSAGE_BID_AMOUNT, exception.getMessage());
	}

	@Test
	public void testValidateBidRequestVOInvalidStartDate() {
		BigDecimal amount = BigDecimal.valueOf(200.0);
		LocalDateTime startDateTime = LocalDateTime.now().plusHours(2);
		LocalDateTime endDateTime = LocalDateTime.now().plusHours(3);
		BigDecimal basePrice = BigDecimal.valueOf(150.0);

		InvalidRequestException exception = assertThrows(InvalidRequestException.class,
				() -> requestValidator.validateBidRequestVO(amount, startDateTime, endDateTime, basePrice));
		assertEquals(ERROR_MESSAGE_PRODUCT_NOT_ACTIVE, exception.getMessage());
	}

}

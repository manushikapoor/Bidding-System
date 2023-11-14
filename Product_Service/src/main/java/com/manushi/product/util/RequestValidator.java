package com.manushi.product.util;

import static com.manushi.product.util.Constants.ERROR_MESSAGE_CATEGORY_EXISTS;
import static com.manushi.product.util.Constants.ERROR_MESSAGE_INVALID_BASE_PRICE;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manushi.product.exceptions.DuplicateDataException;
import com.manushi.product.exceptions.InvalidRequestException;
import com.manushi.product.model.request.CategoryRequestVO;
import com.manushi.product.repository.CategoryRepository;

@Component
public class RequestValidator {

	@Autowired
	CategoryRepository categoryRepository;

	public boolean isDateRangeValid(LocalDateTime startTime, LocalDateTime endTime) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return startTime != null && startTime.isAfter(currentDateTime) && endTime != null && endTime.isAfter(startTime);
	}

	public void validateProductRequestVO(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
			throw new InvalidRequestException(ERROR_MESSAGE_INVALID_BASE_PRICE);
		}
	}

	public void validateCategoryRequestVO(CategoryRequestVO categoryDetails) {
		if (categoryRepository.findByName(categoryDetails.getName()) != null) {
			throw new DuplicateDataException(ERROR_MESSAGE_CATEGORY_EXISTS);
		}
	}

}

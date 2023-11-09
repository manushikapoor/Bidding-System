package com.manushi.product.util;

import static com.manushi.product.util.Constants.ERROR_MESSAGE_CATEGORY_EXISTS;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manushi.product.exceptions.DuplicateDataException;
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

	public void validateCategoryRequestVO(CategoryRequestVO categoryDetails) {
		if (categoryRepository.findByName(categoryDetails.getName()) != null) {
			throw new DuplicateDataException(ERROR_MESSAGE_CATEGORY_EXISTS);
		}
	}

}

package com.manushi.product.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.manushi.product.exceptions.DuplicateDataException;
import com.manushi.product.model.request.CategoryRequestVO;
import com.manushi.product.repository.CategoryRepository;
import com.manushi.product.repository.entity.Category;

public class RequestValidatorTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private RequestValidator requestValidator;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testIsDateRangeValid() {
		// Arrange
		LocalDateTime startTime = LocalDateTime.now().plusDays(1);
		LocalDateTime endTime = LocalDateTime.now().plusDays(2);

		// Act
		boolean result = requestValidator.isDateRangeValid(startTime, endTime);

		// Assert
		assertTrue(result);
	}

	@Test
	public void testIsDateRangeValidWithInvalidDateRange() {
		// Arrange
		LocalDateTime startTime = LocalDateTime.now().plusDays(2);
		LocalDateTime endTime = LocalDateTime.now().plusDays(1);

		// Act
		boolean result = requestValidator.isDateRangeValid(startTime, endTime);

		// Assert
		assertFalse(result);
	}

	@Test
	public void testValidateCategoryRequestVO() {
		// Arrange
		CategoryRequestVO categoryRequestVO = new CategoryRequestVO();
		categoryRequestVO.setName("TestCategory");

		when(categoryRepository.findByName("TestCategory")).thenReturn(null);

		// Act & Assert
		assertDoesNotThrow(() -> requestValidator.validateCategoryRequestVO(categoryRequestVO));
	}

	@Test
	public void testValidateCategoryRequestVOWithDuplicateCategory() {
		// Arrange
		CategoryRequestVO categoryRequestVO = new CategoryRequestVO();
		categoryRequestVO.setName("TestCategory");

		when(categoryRepository.findByName("TestCategory")).thenReturn(new Category());

		// Act & Assert
		DuplicateDataException exception = assertThrows(DuplicateDataException.class,
				() -> requestValidator.validateCategoryRequestVO(categoryRequestVO));
		assertEquals("category already exists", exception.getMessage());
	}
}

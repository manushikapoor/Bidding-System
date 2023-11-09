package com.manushi.user.exceptions.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.manushi.user.exceptions.DataNotFoundException;
import com.manushi.user.exceptions.DuplicateDataException;
import com.manushi.user.exceptions.InvalidRequestException;
import com.manushi.user.model.response.ErrorVO;
import com.manushi.user.util.Constants;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

public class CustomExceptionsHandlerTest {

	@Test
	public void handleInvalidRequestException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();
		InvalidRequestException exception = new InvalidRequestException("Invalid request");

		// Act
		ResponseEntity<ErrorVO> response = handler.handleInvalidRequestException(exception);

		// Assert
		assertErrorResponse(response, HttpStatus.BAD_REQUEST, "InvalidRequestException", "Invalid request");
	}

	@Test
	public void handleConstraintViolationException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();
		ConstraintViolationException exception = mock(ConstraintViolationException.class);
		Set<ConstraintViolation<?>> violations = new HashSet<>();
		ConstraintViolation<?> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn("Constraint violation");
		violations.add(violation);
		when(exception.getConstraintViolations()).thenReturn(violations);

		// Act
		ResponseEntity<ErrorVO> response = handler.handleConstraintViolationException(exception);

		// Assert
		assertErrorResponse(response, HttpStatus.BAD_REQUEST, "InvalidRequestException", "Constraint violation");
	}

	@Test
	public void handleDuplicateDataException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();
		DuplicateDataException exception = new DuplicateDataException("Duplicate data");

		// Act
		ResponseEntity<ErrorVO> response = handler.handleDuplicateDataException(exception);

		// Assert
		assertErrorResponse(response, HttpStatus.CONFLICT, "DuplicateDataException", "Duplicate data");
	}

	@Test
	public void handleDataNotFoundException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();
		DataNotFoundException exception = new DataNotFoundException("Data not found");

		// Act
		ResponseEntity<ErrorVO> response = handler.handleDataNotFoundException(exception);

		// Assert
		assertErrorResponse(response, HttpStatus.NOT_FOUND, "DuplicateDataException", "Data not found");
	}

	@Test
	public void handleBadCredentialsException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();
		BadCredentialsException exception = new BadCredentialsException("Bad credentials");

		// Act
		ResponseEntity<ErrorVO> response = handler.handleUnauthorizedUserException(exception);

		// Assert
		assertErrorResponse(response, HttpStatus.UNAUTHORIZED, "BadCredentialsException", "Bad credentials");
	}

	@Test
	public void handleInvalidDateTimeException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();
		DateTimeParseException exception = new DateTimeParseException("Invalid date", "format", 1);

		// Act
		ResponseEntity<ErrorVO> response = handler.handleInvalidDateTimeException(exception);

		// Assert
		assertErrorResponse(response, HttpStatus.BAD_REQUEST, "InvalidRequestException", Constants.ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION);
	}

	@Test
	public void handleDataAccessException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();
		DataAccessException exception = new DataAccessException("Data access exception") {
		};

		// Act
		ResponseEntity<ErrorVO> response = handler.handleDataAccessException(exception);

		// Assert
		assertErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "InvalidRequestException", Constants.ERROR_MESSAGE_DATABASE_EXCEPTION);
	}

	@Test
	public void handleException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();
		InvalidRequestException exception = new InvalidRequestException("Generic exception");

		// Act
		ResponseEntity<ErrorVO> response = handler.handleException(exception, null);

		// Assert
		assertErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "InvalidRequestException", "Generic exception");
	}

	@Test
	public void testHandleMethodArgumentNotValidException() {
		// Arrange
		CustomExceptionsHandler handler = new CustomExceptionsHandler();

		// Create a BindingResult with field errors
		BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
		bindingResult.addError(new FieldError("fieldName", "errorCode", "error message"));

		// Create a MethodParameter (can be null in this case)
		MethodParameter methodParameter = null;

		// Create a MethodArgumentNotValidException with MethodParameter and
		// BindingResult
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

		// Act
		ResponseEntity<ErrorVO> response = handler.handleMethodArgumentNotValidException(ex);

		// Assert
		assertErrorResponse(response, HttpStatus.BAD_REQUEST, "InvalidRequestException", "error message");
	}

	private void assertErrorResponse(ResponseEntity<ErrorVO> response, HttpStatus expectedStatus, String expectedError, String expectedMessage) {
		assertEquals(expectedStatus, response.getStatusCode());
		ErrorVO error = response.getBody();
		assertNotNull(error);
		assertEquals(expectedError, error.getError());
		assertEquals(expectedMessage, error.getMessage());
	}
}

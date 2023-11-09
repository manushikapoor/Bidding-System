package com.manushi.product.exceptions.advice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.manushi.product.exceptions.DataNotFoundException;
import com.manushi.product.exceptions.DuplicateDataException;
import com.manushi.product.exceptions.InvalidRequestException;
import com.manushi.product.model.response.ErrorVO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ExtendWith(MockitoExtension.class)
public class CustomExceptionsHandlerTest {

	@InjectMocks
	private CustomExceptionsHandler customExceptionsHandler;

	@Test
	public void handleInvalidRequestExceptionTest() {
		InvalidRequestException ex = new InvalidRequestException("Invalid request");
		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleInvalidRequestException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("InvalidRequestException", response.getBody().getError());
		assertEquals("Invalid request", response.getBody().getMessage());
	}

	@Test
	public void handleConstraintViolationExceptionTest() {
		Set<ConstraintViolation<?>> violations = new HashSet<>();
		ConstraintViolation<String> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn("Constraint violation");
		violations.add(violation);

		ConstraintViolationException ex = new ConstraintViolationException("Constraint violation message", violations);
		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleConstraintViolationException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("InvalidRequestException", response.getBody().getError());
		assertEquals("Constraint violation", response.getBody().getMessage());
	}

	@Test
	public void handleDuplicateDataExceptionTest() {
		DuplicateDataException ex = new DuplicateDataException("Duplicate data");
		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleDuplicateDataException(ex);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals("DuplicateDataException", response.getBody().getError());
		assertEquals("Duplicate data", response.getBody().getMessage());
	}

	@Test
	public void handleDataNotFoundExceptionTest() {
		DataNotFoundException ex = new DataNotFoundException("Data not found");
		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleDataNotFoundException(ex);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("DataNotFoundException", response.getBody().getError());
		assertEquals("Data not found", response.getBody().getMessage());
	}

	@Test
	public void handleMethodArgumentNotValidExceptionTest() {
		MethodParameter methodParameter = mock(MethodParameter.class);
		BindingResult bindingResult = new BeanPropertyBindingResult(null, "objectName");
		FieldError fieldError = new FieldError("objectName", "fieldName", "Error message");
		bindingResult.addError(fieldError);

		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleMethodArgumentNotValidException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("InvalidRequestException", response.getBody().getError());
		assertEquals("Error message", response.getBody().getMessage());
	}

	@Test
	public void handleUnauthorizedUserExceptionTest() {
		AuthenticationException ex = mock(AuthenticationException.class);
		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleUnauthorizedUserException(ex);

		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

	@Test
	public void handleInvalidDateTimeExceptionTest() {
		DateTimeParseException ex = new DateTimeParseException("Message", "Parsed data", 0);
		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleInvalidDateTimeException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("InvalidRequestException", response.getBody().getError());
		assertEquals("DateTime parse exception occurred due to invalid format", response.getBody().getMessage());
	}

	@Test
	public void handleDataAccessExceptionTest() {
		DataAccessException ex = mock(DataAccessException.class);
		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleDataAccessException(ex);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void handleExceptionTest() {
		Throwable ex = new RuntimeException("Runtime exception");
		ResponseEntity<ErrorVO> response = customExceptionsHandler.handleException(ex, null);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("RuntimeException", response.getBody().getError());
		assertEquals("Runtime exception", response.getBody().getMessage());
	}
}

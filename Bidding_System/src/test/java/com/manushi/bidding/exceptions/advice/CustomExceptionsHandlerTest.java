package com.manushi.bidding.exceptions.advice;

import static com.manushi.bidding.util.Constants.ERROR_CODE_DATABASE_EXCEPTION;
import static com.manushi.bidding.util.Constants.ERROR_CODE_DATETIME_PARSE_EXCEPTION;
import static com.manushi.bidding.util.Constants.ERROR_CODE_DUPLICATE_DATA_EXCEPTION;
import static com.manushi.bidding.util.Constants.ERROR_CODE_INTERNAL_SERVER_EXCEPTION;
import static com.manushi.bidding.util.Constants.ERROR_CODE_INVALID_REQUEST_EXCEPTION;
import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_DATABASE_EXCEPTION;
import static com.manushi.bidding.util.Constants.ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.manushi.bidding.exceptions.DataNotFoundException;
import com.manushi.bidding.exceptions.DuplicateDataException;
import com.manushi.bidding.exceptions.InvalidRequestException;
import com.manushi.bidding.exceptions.RabbitMqException;
import com.manushi.bidding.model.response.ErrorVO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

@ExtendWith(MockitoExtension.class)
class CustomExceptionsHandlerTest {

	@InjectMocks
	private CustomExceptionsHandler exceptionsHandler;

	@Mock
	private ConstraintViolationException constraintViolationException;

	@Test
	void handleInvalidRequestException() {
		InvalidRequestException ex = new InvalidRequestException("Test message");
		ResponseEntity<ErrorVO> responseEntity = exceptionsHandler.handleInvalidRequestException(ex);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertErrorResponse(responseEntity.getBody(), ex, ERROR_CODE_INVALID_REQUEST_EXCEPTION);
	}

	@Test
	public void handleConstraintViolationExceptionTest() {
		Set<ConstraintViolation<?>> violations = new HashSet<>();
		ConstraintViolation<String> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn("Constraint violation");
		violations.add(violation);

		ConstraintViolationException ex = new ConstraintViolationException("Constraint violation message", violations);
		ResponseEntity<ErrorVO> response = exceptionsHandler.handleConstraintViolationException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("InvalidRequestException", response.getBody().getError());
		assertEquals("Constraint violation", response.getBody().getMessage());
	}

	@Test
	void handleDuplicateDataException() {
		DuplicateDataException ex = new DuplicateDataException("Test message");
		ResponseEntity<ErrorVO> responseEntity = exceptionsHandler.handleDuplicateDataException(ex);
		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertErrorResponse(responseEntity.getBody(), ex, ERROR_CODE_DUPLICATE_DATA_EXCEPTION);
	}

	@Test
	void handleDataNotFoundException() {
		DataNotFoundException ex = new DataNotFoundException("Test message");
		ResponseEntity<ErrorVO> responseEntity = exceptionsHandler.handleDataNotFoundException(ex);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertErrorResponse(responseEntity.getBody(), ex, ERROR_CODE_DUPLICATE_DATA_EXCEPTION);
	}

	@Test
	public void handleMethodArgumentNotValidExceptionTest() {
		MethodParameter methodParameter = mock(MethodParameter.class);
		BindingResult bindingResult = new BeanPropertyBindingResult(null, "objectName");
		FieldError fieldError = new FieldError("objectName", "fieldName", "Error message");
		bindingResult.addError(fieldError);

		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

		ResponseEntity<ErrorVO> response = exceptionsHandler.handleMethodArgumentNotValidException(ex);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("InvalidRequestException", response.getBody().getError());
		assertEquals("Error message", response.getBody().getMessage());
	}

	@Test
	void handleRabbitMqException() {
		RabbitMqException ex = new RabbitMqException("Test message");
		ResponseEntity<ErrorVO> responseEntity = exceptionsHandler.handleRabbitMqException(ex);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertErrorResponse(responseEntity.getBody(), ex, ERROR_CODE_INTERNAL_SERVER_EXCEPTION);
	}

	@Test
	void handleUnauthorizedUserException() {
		Exception ex = new BadCredentialsException("Test message");
		ResponseEntity<ErrorVO> responseEntity = exceptionsHandler.handleUnauthorizedUserException(ex);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		assertErrorResponse(responseEntity.getBody(), ex, ERROR_CODE_INVALID_REQUEST_EXCEPTION);
	}

	@Test
	void handleInvalidDateTimeException() {
		Exception ex = new DateTimeParseException("Test message", "format", 0);
		ResponseEntity<ErrorVO> responseEntity = exceptionsHandler.handleInvalidDateTimeException(ex);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertErrorResponse(responseEntity.getBody(), new InvalidRequestException(ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION),
				ERROR_CODE_DATETIME_PARSE_EXCEPTION);
	}

	@Test
	void handleDataAccessException() {
		Exception ex = new DataAccessException("Test message") {
		};
		ResponseEntity<ErrorVO> responseEntity = exceptionsHandler.handleDataAccessException(ex);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertErrorResponse(responseEntity.getBody(), new InvalidRequestException(ERROR_MESSAGE_DATABASE_EXCEPTION), ERROR_CODE_DATABASE_EXCEPTION);
	}

	@Test
	void handleException() {
		Exception ex = new Exception("Test message");
		ResponseEntity<ErrorVO> responseEntity = exceptionsHandler.handleException(ex, null);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
		assertErrorResponse(responseEntity.getBody(), ex, ERROR_CODE_INTERNAL_SERVER_EXCEPTION);
	}

	private void assertErrorResponse(ErrorVO errorResponse, Throwable ex, String errorCode) {
		assertNotNull(errorResponse);
		assertEquals(ex.getClass().getSimpleName(), errorResponse.getError());
		assertEquals(ex.getMessage(), errorResponse.getMessage());
		assertEquals(errorCode, errorResponse.getErrorCode());
	}

	private ConstraintViolation<?> createConstraintViolation(String message) {
		ConstraintViolation<?> violation = mock(ConstraintViolation.class);
		when(violation.getMessage()).thenReturn(message);
		Path path = mock(Path.class);
		when(path.toString()).thenReturn("field");
		when(violation.getPropertyPath()).thenReturn(path);
		return violation;
	}

}

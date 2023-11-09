package com.manushi.user.exceptions.advice;

import static com.manushi.user.util.Constants.ERROR_CODE_DATABASE_EXCEPTION;
import static com.manushi.user.util.Constants.ERROR_CODE_DATA_NOT_FOUND_EXCEPTION;
import static com.manushi.user.util.Constants.ERROR_CODE_DATETIME_PARSE_EXCEPTION;
import static com.manushi.user.util.Constants.ERROR_CODE_DUPLICATE_DATA_EXCEPTION;
import static com.manushi.user.util.Constants.ERROR_CODE_INVALID_REQUEST_EXCEPTION;
import static com.manushi.user.util.Constants.ERROR_MESSAGE_DATABASE_EXCEPTION;
import static com.manushi.user.util.Constants.ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION;
import static com.manushi.user.util.Constants.LOG_PATTERN;

import java.time.format.DateTimeParseException;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.manushi.user.exceptions.DataNotFoundException;
import com.manushi.user.exceptions.DuplicateDataException;
import com.manushi.user.exceptions.InvalidRequestException;
import com.manushi.user.model.response.ErrorVO;
import com.manushi.user.repository.enums.ExceptionType;
import com.manushi.user.util.Constants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Validated
@ControllerAdvice
@Slf4j
public class CustomExceptionsHandler {

	@ExceptionHandler({ InvalidRequestException.class, MissingServletRequestParameterException.class, HttpClientErrorException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ErrorVO> handleInvalidRequestException(Exception ex) {
		log.info("BadRequestException occurred- message: [{}], error code: [{}]", ex.getMessage(), ERROR_CODE_INVALID_REQUEST_EXCEPTION);
		return ResponseEntity.badRequest().body(buildErrorResponse(new InvalidRequestException(ex.getMessage()), ERROR_CODE_INVALID_REQUEST_EXCEPTION));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ErrorVO> handleConstraintViolationException(ConstraintViolationException ex) {
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		if (!violations.isEmpty()) {
			ConstraintViolation<?> firstViolation = violations.iterator().next();
			String errorMessage = firstViolation.getMessage();
			log.info("BadRequestException occurred- message:[{}], error code: [{}]", errorMessage, ERROR_CODE_INVALID_REQUEST_EXCEPTION);
			return ResponseEntity.badRequest().body(buildErrorResponse(new InvalidRequestException(errorMessage), ERROR_CODE_INVALID_REQUEST_EXCEPTION));
		}
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(DuplicateDataException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public final ResponseEntity<ErrorVO> handleDuplicateDataException(DuplicateDataException ex) {
		log.info("DuplicateDataException occurred - message: [{}], error code: [{}]", ex.getMessage(), ERROR_CODE_DUPLICATE_DATA_EXCEPTION);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(buildErrorResponse(new DuplicateDataException(ex.getMessage()), ERROR_CODE_DUPLICATE_DATA_EXCEPTION));
	}

	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public final ResponseEntity<ErrorVO> handleDataNotFoundException(DataNotFoundException ex) {
		log.info("DataNotFoundException occurred - message: [{}], error code: [{}]", ex.getMessage(), ERROR_CODE_DATA_NOT_FOUND_EXCEPTION);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildErrorResponse(new DuplicateDataException(ex.getMessage()), ERROR_CODE_DUPLICATE_DATA_EXCEPTION));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ErrorVO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		String errorMessage = result.getFieldErrors().get(0).getDefaultMessage();
		log.info("InvalidRequestException occurred - message: [{}], error code: [{}]", errorMessage, ERROR_CODE_INVALID_REQUEST_EXCEPTION);
		return ResponseEntity.badRequest().body(buildErrorResponse(new InvalidRequestException(errorMessage), ERROR_CODE_INVALID_REQUEST_EXCEPTION));
	}

	@ExceptionHandler({ AuthenticationException.class, BadCredentialsException.class })
	public final ResponseEntity<ErrorVO> handleUnauthorizedUserException(Exception ex) {
		log.info("BadCredentialsException occurred: [{}]", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(buildErrorResponse(ex, ERROR_CODE_INVALID_REQUEST_EXCEPTION));
	}

	@ExceptionHandler({ DateTimeParseException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<ErrorVO> handleInvalidDateTimeException(Exception ex) {
		log.info("DateTimeParseException occurred- message: [{}], error code: [{}]", ex.getMessage(), ERROR_CODE_DATETIME_PARSE_EXCEPTION);
		return ResponseEntity.badRequest().body(buildErrorResponse(new InvalidRequestException(ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION), ERROR_CODE_DATETIME_PARSE_EXCEPTION));
	}

	@ExceptionHandler({ DataAccessException.class, DataAccessResourceFailureException.class, DataRetrievalFailureException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final ResponseEntity<ErrorVO> handleDataAccessException(Exception ex) {
		log.error(LOG_PATTERN, Constants.USER_SERVICE, ExceptionType.DATABASE_ERROR, Constants.ERROR_CODE_DATABASE_EXCEPTION, ex);
		return ResponseEntity.internalServerError().body(buildErrorResponse(new InvalidRequestException(ERROR_MESSAGE_DATABASE_EXCEPTION), ERROR_CODE_DATABASE_EXCEPTION));
	}

	@ExceptionHandler
	public final ResponseEntity<ErrorVO> handleException(Throwable ex, HttpServletRequest request) {
		log.error(LOG_PATTERN, Constants.USER_SERVICE, ExceptionType.INTERNAL_SERVER_ERROR, Constants.ERROR_CODE_INTERNAL_SERVER_EXCEPTION, ex);
		return ResponseEntity.internalServerError().body(buildErrorResponse(ex, Constants.ERROR_CODE_INTERNAL_SERVER_EXCEPTION));
	}

	private ErrorVO buildErrorResponse(Throwable exception, String errorCode) {
		ErrorVO errorResponse = new ErrorVO();
		errorResponse.setError(exception.getClass().getSimpleName());
		errorResponse.setMessage(exception.getMessage());
		errorResponse.setErrorCode(errorCode);
		return errorResponse;
	}

}

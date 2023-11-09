package com.manushi.user.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConstantsTest {

	@Test
	public void testConstants() {
		assertEquals("USER_SERVICE", Constants.USER_SERVICE);
		assertEquals("\"{}::{}: {}: {}\"", Constants.LOG_PATTERN);
		assertEquals("USER-SERVICE-1001", Constants.ERROR_CODE_INVALID_REQUEST_EXCEPTION);
		assertEquals("USER-SERVICE-1002", Constants.ERROR_CODE_DATETIME_PARSE_EXCEPTION);
		assertEquals("USER-SERVICE-1003", Constants.ERROR_CODE_DATABASE_EXCEPTION);
		assertEquals("USER-SERVICE-1004", Constants.ERROR_CODE_INTERNAL_SERVER_EXCEPTION);
		assertEquals("USER-SERVICE-1005", Constants.ERROR_CODE_DUPLICATE_DATA_EXCEPTION);
		assertEquals("USER-SERVICE-1006", Constants.ERROR_CODE_DATA_NOT_FOUND_EXCEPTION);
		assertEquals("Username is already taken", Constants.ERROR_USERNAME_TAKEN);
		assertEquals("Email already exists", Constants.ERROR_EMAIL_EXISTS);
		assertEquals("Role doesn't exist", Constants.ERROR_ROLE_NOT_FOUND);
		assertEquals("DateTime parse exception occurred due to invalid format", Constants.ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION);
		assertEquals("Expected data could not be retrieved due to database exception", Constants.ERROR_MESSAGE_DATABASE_EXCEPTION);
		assertEquals("Invalid Credentials", Constants.ERROR_MESSAGE_INVALID_CREDENTIALS);
		assertEquals("Invalid email", Constants.ERROR_MSG_INVALID_EMAIL);
		assertEquals("Invalid password", Constants.ERROR_MSG_INVALID_PASSWORD);
		assertEquals("Invalid JWT Token", Constants.ERROR_MSG_INVALID_JWT_TOKEN);
		assertEquals("\\b[A-Za-z0-9'._-]+@[A-Za-z0-9'.-]+\\.[A-Za-z0-9._-]+\\b", Constants.EMAIL_VALIDATION_REGEX);
		assertEquals("/v1", Constants.V1);
		assertEquals("/user", Constants.USER);
		assertEquals("/signup", Constants.SIGNUP);
		assertEquals("/signin", Constants.SIGNIN);
		assertEquals("/role", Constants.ROLE);
	}
}

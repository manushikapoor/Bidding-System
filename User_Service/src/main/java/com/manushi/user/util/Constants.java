package com.manushi.user.util;

public class Constants {

	public static final String USER_SERVICE = "USER_SERVICE";

	public static final String LOG_PATTERN = "\"{}::{}: {}: {}\"";

	public static final String ERROR_CODE_INVALID_REQUEST_EXCEPTION = "USER-SERVICE-1001";
	public static final String ERROR_CODE_DATETIME_PARSE_EXCEPTION = "USER-SERVICE-1002";
	public static final String ERROR_CODE_DATABASE_EXCEPTION = "USER-SERVICE-1003";
	public static final String ERROR_CODE_INTERNAL_SERVER_EXCEPTION = "USER-SERVICE-1004";
	public static final String ERROR_CODE_DUPLICATE_DATA_EXCEPTION = "USER-SERVICE-1005";
	public static final String ERROR_CODE_DATA_NOT_FOUND_EXCEPTION = "USER-SERVICE-1006";

	public static final String ERROR_USERNAME_TAKEN = "Username is already taken";
	public static final String ERROR_EMAIL_EXISTS = "Email already exists";
	public static final String ERROR_ROLE_NOT_FOUND = "Role doesn't exist";
	public static final String ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION = "DateTime parse exception occurred due to invalid format";
	public static final String ERROR_MESSAGE_DATABASE_EXCEPTION = "Expected data could not be retrieved due to database exception";
	public static final String ERROR_MESSAGE_INVALID_CREDENTIALS = "Invalid Credentials";
	public static final String ERROR_MSG_INVALID_EMAIL = "Invalid email";
	public static final String ERROR_MSG_INVALID_PASSWORD = "Invalid password";
	public static final String ERROR_MSG_INVALID_JWT_TOKEN = "Invalid JWT Token";

	public static final String EMAIL_VALIDATION_REGEX = "\\b[A-Za-z0-9'._-]+@[A-Za-z0-9'.-]+\\.[A-Za-z0-9._-]+\\b";

	public static final String V1 = "/v1";
	public static final String USER = "/user";
	public static final String SIGNUP = "/signup";
	public static final String SIGNIN = "/signin";
	public static final String ROLE = "/role";
}

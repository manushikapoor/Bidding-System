package com.manushi.product.util;

public class Constants {

	public static final String PRODUCT_SERVICE = "PRODUCT_SERVICE";

	public static final String LOG_PATTERN = "\"{}::{}: {}: {}\"";
	public static final String V1 = "/v1";
	public static final String PRODUCT = "/product";
	public static final String CATEGORY = "/category";

	public static final String ERROR_CODE_INVALID_REQUEST_EXCEPTION = "PRODUCT_SERVICE-1001";
	public static final String ERROR_CODE_DATETIME_PARSE_EXCEPTION = "PRODUCT_SERVICE-1002";
	public static final String ERROR_CODE_DATABASE_EXCEPTION = "PRODUCT_SERVICE-1003";
	public static final String ERROR_CODE_INTERNAL_SERVER_EXCEPTION = "PRODUCT_SERVICE-1004";
	public static final String ERROR_CODE_DUPLICATE_DATA_EXCEPTION = "PRODUCT-SERVICE-1005";
	public static final String ERROR_CODE_DATA_NOT_FOUND_EXCEPTION = "PRODUCT-SERVICE-1006";

	public static final String ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION = "DateTime parse exception occurred due to invalid format";
	public static final String ERROR_MESSAGE_DATABASE_EXCEPTION = "Expected data could not be retrieved due to database exception";
	public static final String ERROR_MESSAGE_INVALID_DATES = "Invalid dates";
	public static final String ERROR_MESSAGE_PRODUCT_NOT_FOUND = "Product not found";
	public static final String ERROR_MESSAGE_USERNAME_NOT_FOUND = "username not found";
	public static final String ERROR_MESSAGE_CATEGORY_EXISTS = "category already exists";
	public static final String ERROR_MESSAGE_CATEGORY_NOT_FOUND = "Category not found";
	public static final String ERROR_MESSAGE_INVALID_BASE_PRICE = "Base price can't be less than 0";

}

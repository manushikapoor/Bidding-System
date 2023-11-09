package com.manushi.bidding.util;

public class Constants {

	public static final String BIDDING_SERVICE = "BIDDING_SERVICE";

	public static final String LOG_PATTERN = "\"{}::{}: {}: {}\"";
	public static final String V1 = "/v1";
	public static final String BIDS = "/bids";
	public static final String USER = "/user";
	public static final String PRODUCT = "/product";
	public static final String NOTIFY = "/notify";
	public static final String SEND_MAIL = "/send-mail";

	public static final String ERROR_CODE_INVALID_REQUEST_EXCEPTION = "BIDDING_SERVICE-1001";
	public static final String ERROR_CODE_DATETIME_PARSE_EXCEPTION = "BIDDING_SERVICE-1002";
	public static final String ERROR_CODE_DATABASE_EXCEPTION = "BIDDING_SERVICE-1003";
	public static final String ERROR_CODE_INTERNAL_SERVER_EXCEPTION = "BIDDING_SERVICE-1004";
	public static final String ERROR_CODE_DUPLICATE_DATA_EXCEPTION = "BIDDING-SERVICE-1005";
	public static final String ERROR_CODE_DATA_NOT_FOUND_EXCEPTION = "BIDDING-SERVICE-1006";

	public static final String ERROR_MESSAGE_DATETIME_PARSE_EXCEPTION = "DateTime parse exception occurred due to invalid format";
	public static final String ERROR_MESSAGE_DATABASE_EXCEPTION = "Expected data could not be retrieved due to database exception";
	public static final String ERROR_MESSAGE_INVALID_DATES = "Invalid dates";
	public static final String ERROR_MESSAGE_PRODUCT_NOT_FOUND = "Product not found";
	public static final String ERROR_MESSAGE_BID_AMOUNT = "Bid amount can't be less than the product base price";
	public static final String ERROR_MESSAGE_USER_NOT_FOUND = "User not found";

}

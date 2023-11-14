package com.manushi.product.service.authorization;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthorizationHandlerService {

	String getAuthorizationHeader(HttpServletRequest request);

}

package com.manushi.bidding.service.authorization;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthorizationHandlerService {
	public String getAuthorizationHeader(HttpServletRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("Authorization header cannot be null");
		}
		return request.getHeader("Authorization");
	}
}

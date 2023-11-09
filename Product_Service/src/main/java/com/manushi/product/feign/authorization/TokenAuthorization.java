package com.manushi.product.feign.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenAuthorization {

	@Autowired
	private UserFeignClient userFeignClient;

	public boolean validateTokenAndCheckRole(String token, String requiredRole) {

		String role = userFeignClient.validateTokenAndFetchRoles(token);

		return role.equals(requiredRole);
	}
}

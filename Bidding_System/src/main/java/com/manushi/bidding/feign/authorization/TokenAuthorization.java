package com.manushi.bidding.feign.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthorization {

	@Autowired
	private UserFeignClient userFeignClient;

	public boolean validateTokenAndCheckRole(String token, String requiredRole) {

		String role = userFeignClient.validateTokenAndFetchRoles(token);

		return role.equals(requiredRole);
	}
}

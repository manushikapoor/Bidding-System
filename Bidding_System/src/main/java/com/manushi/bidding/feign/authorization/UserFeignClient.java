package com.manushi.bidding.feign.authorization;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${user-service.url}")
public interface UserFeignClient {
	@GetMapping("/v1/user/role")
	String validateTokenAndFetchRoles(@RequestParam("Authorization") String authorizationHeader);
}
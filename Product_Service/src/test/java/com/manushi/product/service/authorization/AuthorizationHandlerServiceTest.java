package com.manushi.product.service.authorization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.mock.web.MockHttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;

public class AuthorizationHandlerServiceTest {

	@Test
	public void testGetAuthorizationHeader() {
		// Create a MockHttpServletRequest and set the "Authorization" header
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization", "Bearer YourTokenHere");

		// Create an instance of AuthorizationHandlerService
		AuthorizationHandlerService authorizationHandlerService = new AuthorizationHandlerService();

		// Call the method to test
		String authorizationHeader = authorizationHandlerService.getAuthorizationHeader(request);

		// Assert that the authorization header matches the expected value
		assertEquals("Bearer YourTokenHere", authorizationHeader);
	}

	@Test
	public void testGetAuthorizationHeaderWithNullRequest() {
		// Arrange
		AuthorizationHandlerService authorizationHandlerService = new AuthorizationHandlerService();
		HttpServletRequest nullRequest = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, new Executable() {
			@Override
			public void execute() {
				authorizationHandlerService.getAuthorizationHeader(nullRequest);
			}
		});
	}
}

package com.manushi.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.manushi.user.model.request.UserSignInRequestVO;
import com.manushi.user.model.request.UserSignUpRequestVO;
import com.manushi.user.model.response.JwtResponse;
import com.manushi.user.model.response.UserDetailsVO;
import com.manushi.user.service.auth.AuthorizationService;

@ExtendWith(MockitoExtension.class)
public class AuthorizationControllerTest {

	@InjectMocks
	private AuthorizationController authorizationController;

	@Mock
	private AuthorizationService authorizationService;

	@Test
	public void testSignup_Success() {
		UserSignUpRequestVO user = new UserSignUpRequestVO();
		UserDetailsVO userDetails = new UserDetailsVO(); // You need to create a UserDetailsVO with sample data here.

		when(authorizationService.signup(user)).thenReturn(userDetails);

		ResponseEntity<UserDetailsVO> response = authorizationController.signup(user);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(userDetails, response.getBody());
	}

	@Test
	public void testSignIn_Success() {
		UserSignInRequestVO user = new UserSignInRequestVO();
		JwtResponse jwtResponse = new JwtResponse("token"); // You need to create a JwtResponse object with a token.

		when(authorizationService.authenticateUser(user)).thenReturn(jwtResponse);

		ResponseEntity<JwtResponse> response = authorizationController.signIn(user);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(jwtResponse, response.getBody());
	}

	@Test
	public void testValidateTokenAndFetchRole_Success() {
		String authorizationHeader = "Bearer valid_token";
		String role = "ROLE_USER"; // You need to define the expected role.

		when(authorizationService.validateTokenAndFetchRole(authorizationHeader)).thenReturn(role);

		ResponseEntity<String> response = authorizationController.validateTokenAndFetchRole(authorizationHeader);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(role, response.getBody());
	}

	@Test
	public void testValidateTokenAndFetchRole_InvalidToken() {
		String invalidAuthorizationHeader = "InvalidToken";

		doThrow(MethodArgumentTypeMismatchException.class).when(authorizationService).validateTokenAndFetchRole(invalidAuthorizationHeader);

		assertThrows(MethodArgumentTypeMismatchException.class, () -> authorizationController.validateTokenAndFetchRole(invalidAuthorizationHeader));
	}
}

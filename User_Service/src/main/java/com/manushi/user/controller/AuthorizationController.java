package com.manushi.user.controller;

import static com.manushi.user.util.Constants.ROLE;
import static com.manushi.user.util.Constants.SIGNIN;
import static com.manushi.user.util.Constants.SIGNUP;
import static com.manushi.user.util.Constants.USER;
import static com.manushi.user.util.Constants.V1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manushi.user.model.request.UserSignInRequestVO;
import com.manushi.user.model.request.UserSignUpRequestVO;
import com.manushi.user.model.response.JwtResponse;
import com.manushi.user.model.response.OperationSuccessVO;
import com.manushi.user.service.auth.AuthorizationService;

import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(V1 + USER)
public class AuthorizationController {

	@Autowired
	private final AuthorizationService authorizationService;

	@PostMapping(SIGNUP)
	@ApiOperation(value = "Get a greeting message", notes = "Returns a simple greeting message.")
	public ResponseEntity<OperationSuccessVO> signup(@Valid @RequestBody UserSignUpRequestVO user) {
		authorizationService.signup(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new OperationSuccessVO());
	}

	@PostMapping(SIGNIN)
	@ApiOperation(value = "Get a greeting message", notes = "Returns a simple greeting message.")
	public ResponseEntity<JwtResponse> signIn(@Valid @RequestBody UserSignInRequestVO user) {
		return ResponseEntity.ok(authorizationService.authenticateUser(user));
	}

	@GetMapping(ROLE)
	@ApiOperation(value = "Get a greeting message", notes = "Returns a simple greeting message.")
	public ResponseEntity<String> validateTokenAndFetchRole(@RequestParam("Authorization") String authorizationHeader) {
		String role = authorizationService.validateTokenAndFetchRole(authorizationHeader);
		return ResponseEntity.ok(role);
	}
}
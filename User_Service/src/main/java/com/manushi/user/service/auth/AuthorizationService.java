package com.manushi.user.service.auth;

import com.manushi.user.model.request.UserSignInRequestVO;
import com.manushi.user.model.request.UserSignUpRequestVO;
import com.manushi.user.model.response.JwtResponse;

public interface AuthorizationService {

	void signup(UserSignUpRequestVO user);

	JwtResponse authenticateUser(UserSignInRequestVO user);

	String validateTokenAndFetchRole(String authorizationHeader);

}

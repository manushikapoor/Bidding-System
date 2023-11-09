package com.manushi.user.service.auth;

import com.manushi.user.model.request.UserSignInRequestVO;
import com.manushi.user.model.request.UserSignUpRequestVO;
import com.manushi.user.model.response.JwtResponse;
import com.manushi.user.model.response.UserDetailsVO;

public interface AuthorizationService {

	UserDetailsVO signup(UserSignUpRequestVO user);

	JwtResponse authenticateUser(UserSignInRequestVO user);

	String validateTokenAndFetchRole(String authorizationHeader);

}

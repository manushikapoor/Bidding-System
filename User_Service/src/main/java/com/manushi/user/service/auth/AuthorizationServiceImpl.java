package com.manushi.user.service.auth;

import static com.manushi.user.util.Constants.ERROR_MSG_INVALID_JWT_TOKEN;
import static com.manushi.user.util.Constants.ERROR_MSG_INVALID_PASSWORD;
import static com.manushi.user.util.Constants.ERROR_ROLE_NOT_FOUND;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manushi.user.exceptions.DataNotFoundException;
import com.manushi.user.model.request.UserSignInRequestVO;
import com.manushi.user.model.request.UserSignUpRequestVO;
import com.manushi.user.model.response.JwtResponse;
import com.manushi.user.model.response.UserDetailsVO;
import com.manushi.user.repository.RoleRepository;
import com.manushi.user.repository.UserRepository;
import com.manushi.user.repository.VendorRepository;
import com.manushi.user.repository.entity.Roles;
import com.manushi.user.repository.entity.Users;
import com.manushi.user.repository.entity.Vendors;
import com.manushi.user.util.JwtUtil;
import com.manushi.user.util.RequestValidator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final VendorRepository vendorRepository;

	@Autowired
	private final RoleRepository roleRepository;

	@Autowired
	private final RequestValidator validator;

	@Autowired
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private final JwtUtil jwtUtil;

	@Autowired
	private final UserDetailsService userDetailsService;

	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public UserDetailsVO signup(UserSignUpRequestVO userSignUpRequestVO) {
		// Validate request object
		validator.validate(userSignUpRequestVO);
		Roles role = roleRepository.findByRoleName(userSignUpRequestVO.getRole());
		if (role == null) {
			throw new DataNotFoundException(ERROR_ROLE_NOT_FOUND);
		}
		Vendors vendor = null;
		if (userSignUpRequestVO.getVendor() != null) {
			// If the "vendor" field is provided in the request, find it in the repo
			vendor = vendorRepository.findByVendorName(userSignUpRequestVO.getVendor());
		}
		// If vendor doesn't already exist and "vendor" is not provided, do nothing;
		// otherwise, create one
		if (vendor == null && userSignUpRequestVO.getVendor() != null && !userSignUpRequestVO.getVendor().isEmpty()) {
			vendor = Vendors.builder().vendorName(userSignUpRequestVO.getVendor()).build();
			vendor = vendorRepository.save(vendor);
			log.info("Vendor successfully created with name - {} ", vendor.getVendorName());
		}
		Users user = Users.builder().email(userSignUpRequestVO.getEmail()).name(userSignUpRequestVO.getName()).userName(userSignUpRequestVO.getUsername()).password(passwordEncoder.encode(userSignUpRequestVO.getPassword())).role(role).vendor(vendor).build();
		userRepository.save(user);
		log.info("User successfully created with username - {} ", user.getUserName());
		UserDetailsVO.UserDetailsVOBuilder userDetailsBuilder = UserDetailsVO.builder().email(user.getEmail()).name(user.getName()).role(user.getRole().getRoleName()).username(user.getUserName());

		if (user.getVendor() != null) {
			userDetailsBuilder.vendor(user.getVendor().getVendorName());
		}

		UserDetailsVO userDetails = userDetailsBuilder.build();

		return userDetails;
	}

	@Override
	public JwtResponse authenticateUser(UserSignInRequestVO userSignInRequestVO) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(userSignInRequestVO.getUsername());
		Users user = userRepository.findByUserName(userSignInRequestVO.getUsername());
		String storedPassword = user.getPassword();
		// Use BCrypt to compare the plaintext password with the hashed password
		if (bCryptPasswordEncoder.matches(userSignInRequestVO.getPassword(), storedPassword)) {
			// If the passwords match, the user is authenticated, generate jwt token
			final String token = jwtUtil.generateToken(userDetails);
			log.info("User authenticated with username - {} ", user.getUserName());
			return new JwtResponse(token);
		} else {
			// Passwords don't match, return an unauthorized response
			throw new BadCredentialsException(ERROR_MSG_INVALID_PASSWORD);
		}
	}

	@Override
	public String validateTokenAndFetchRole(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			// Extract the token from the "Authorization" header
			String token = authorizationHeader.substring(7);
			String username = jwtUtil.extractUsername(token);
			Users user = userRepository.findByUserName(username);
			if (user != null && user.getRole() != null) {
				// User and role exist
				log.info("Token validated for user - {} ", user.getUserName());
				return user.getRole().getRoleName();
			}
		}
		// Invalid or missing token
		throw new BadCredentialsException(ERROR_MSG_INVALID_JWT_TOKEN);
	}

}

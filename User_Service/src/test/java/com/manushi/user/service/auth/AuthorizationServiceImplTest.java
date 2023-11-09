package com.manushi.user.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private VendorRepository vendorRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private RequestValidator validator;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtUtil jwtUtil;

	@Mock
	private UserDetailsService userDetailsService;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	private AuthorizationServiceImpl authorizationService;

	@BeforeEach
	void setUp() {
		// Resetting the Mockito mocks before each test
		org.mockito.Mockito.reset(userRepository, vendorRepository, roleRepository, validator, passwordEncoder, jwtUtil, userDetailsService, bCryptPasswordEncoder);
	}

	@Test
	public void testSignupSuccess() {
		// Arrange
		UserSignUpRequestVO userSignUpRequestVO = new UserSignUpRequestVO();
		userSignUpRequestVO.setEmail("test@example.com");
		userSignUpRequestVO.setName("Test User");
		userSignUpRequestVO.setUsername("testuser");
		userSignUpRequestVO.setPassword("password");
		userSignUpRequestVO.setRole("ROLE_USER");
		userSignUpRequestVO.setVendor("Test Vendor");

		Roles role = new Roles();
		role.setRoleName("ROLE_USER");

		Vendors vendor = new Vendors();
		vendor.setVendorName("Test Vendor");

		Users user = new Users();
		user.setId(1L);
		user.setEmail(userSignUpRequestVO.getEmail());
		user.setName(userSignUpRequestVO.getName());
		user.setUserName(userSignUpRequestVO.getUsername());
		user.setPassword("hashedPassword");
		user.setRole(role);
		user.setVendor(vendor);

		doNothing().when(validator).validate(userSignUpRequestVO);
		when(roleRepository.findByRoleName(userSignUpRequestVO.getRole())).thenReturn(role);
		when(vendorRepository.findByVendorName(userSignUpRequestVO.getVendor())).thenReturn(vendor);
		when(userRepository.save(any(Users.class))).thenReturn(user);

		// Act
		UserDetailsVO userDetailsVO = authorizationService.signup(userSignUpRequestVO);

		// Assert
		assertNotNull(userDetailsVO);
		// Add more assertions as needed
	}

	@Test
	public void testSignupSuccessNoVendor() {
		// Arrange
		UserSignUpRequestVO userSignUpRequestVO = new UserSignUpRequestVO();
		userSignUpRequestVO.setEmail("test@example.com");
		userSignUpRequestVO.setName("Test User");
		userSignUpRequestVO.setUsername("testuser");
		userSignUpRequestVO.setPassword("password");
		userSignUpRequestVO.setRole("ROLE_USER");
		userSignUpRequestVO.setVendor("Test Vendor");

		Roles role = new Roles();
		role.setRoleName("ROLE_USER");

		Vendors vendor = new Vendors();
		vendor.setVendorName("Test Vendor");

		Users user = new Users();
		user.setId(1L);
		user.setEmail(userSignUpRequestVO.getEmail());
		user.setName(userSignUpRequestVO.getName());
		user.setUserName(userSignUpRequestVO.getUsername());
		user.setPassword("hashedPassword");
		user.setRole(role);
		user.setVendor(vendor);

		doNothing().when(validator).validate(userSignUpRequestVO);
		when(roleRepository.findByRoleName(userSignUpRequestVO.getRole())).thenReturn(role);
		when(vendorRepository.findByVendorName(userSignUpRequestVO.getVendor())).thenReturn(null);
		when(userRepository.save(any(Users.class))).thenReturn(user);
		when(vendorRepository.save(any(Vendors.class))).thenReturn(vendor);

		// Act
		UserDetailsVO userDetailsVO = authorizationService.signup(userSignUpRequestVO);

		// Assert
		assertNotNull(userDetailsVO);
		// Add more assertions as needed
	}

	@Test
	public void testSignupRoleNotFound() {
		// Arrange
		UserSignUpRequestVO userSignUpRequestVO = new UserSignUpRequestVO();
		userSignUpRequestVO.setRole("INVALID_ROLE");

		doNothing().when(validator).validate(userSignUpRequestVO);
		when(roleRepository.findByRoleName(userSignUpRequestVO.getRole())).thenReturn(null);

		// Act and Assert
		assertThrows(DataNotFoundException.class, () -> authorizationService.signup(userSignUpRequestVO));
	}

	@Test
	public void testSignupWithNullVendor() {
		// Arrange
		UserSignUpRequestVO userSignUpRequestVO = new UserSignUpRequestVO();
		userSignUpRequestVO.setRole("ROLE_USER");
		userSignUpRequestVO.setVendor(null);

		Roles role = new Roles();
		role.setRoleName("ROLE_USER");

		doNothing().when(validator).validate(userSignUpRequestVO);
		when(roleRepository.findByRoleName(userSignUpRequestVO.getRole())).thenReturn(role);

		// Act
		UserDetailsVO userDetailsVO = authorizationService.signup(userSignUpRequestVO);

		// Assert
		assertNotNull(userDetailsVO);
		// Add more assertions as needed
	}

	@Test
	public void testAuthenticateUserSuccess() {
		// Arrange
		UserSignInRequestVO userSignInRequestVO = new UserSignInRequestVO();
		userSignInRequestVO.setUsername("testuser");
		userSignInRequestVO.setPassword("password");

		Users user = new Users();
		user.setUserName("testuser");
		user.setPassword("$2a$10$JmITtU0ePjhsrCKcR5QyjeF/mnK2Hfb5J9sNcXelj2Qx2XzPm/Bsy"); // BCrypt hashed password

		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUserName()).password(user.getPassword()).authorities("ROLE_USER").build();

		when(userRepository.findByUserName(userSignInRequestVO.getUsername())).thenReturn(user);
		when(bCryptPasswordEncoder.matches(userSignInRequestVO.getPassword(), user.getPassword())).thenReturn(true);
		when(userDetailsService.loadUserByUsername(userSignInRequestVO.getUsername())).thenReturn(userDetails);
		when(jwtUtil.generateToken(userDetails)).thenReturn("testToken");

		// Act
		JwtResponse jwtResponse = authorizationService.authenticateUser(userSignInRequestVO);

		// Assert
		assertNotNull(jwtResponse);
		assertEquals("testToken", jwtResponse.getToken());
	}

	@Test
	public void testAuthenticateUserInvalidPassword() {
		// Arrange
		UserSignInRequestVO userSignInRequestVO = new UserSignInRequestVO();
		userSignInRequestVO.setUsername("testuser");
		userSignInRequestVO.setPassword("invalidPassword");

		Users user = new Users();
		user.setUserName("testuser");
		user.setPassword("$2a$10$JmITtU0ePjhsrCKcR5QyjeF/mnK2Hfb5J9sNcXelj2Qx2XzPm/Bsy"); // BCrypt hashed password

		when(userRepository.findByUserName(userSignInRequestVO.getUsername())).thenReturn(user);
		when(bCryptPasswordEncoder.matches(userSignInRequestVO.getPassword(), user.getPassword())).thenReturn(false);

		// Act and Assert
		assertThrows(BadCredentialsException.class, () -> authorizationService.authenticateUser(userSignInRequestVO));
	}

	@Test
	public void testValidateTokenAndFetchRoleInvalidToken() {
		// Arrange
		String invalidToken = "InvalidToken";

		// Act and Assert
		assertThrows(BadCredentialsException.class, () -> authorizationService.validateTokenAndFetchRole(invalidToken));
	}
}

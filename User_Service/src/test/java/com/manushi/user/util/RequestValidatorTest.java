package com.manushi.user.util;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.manushi.user.exceptions.DuplicateDataException;
import com.manushi.user.exceptions.InvalidRequestException;
import com.manushi.user.model.request.UserSignUpRequestVO;
import com.manushi.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class RequestValidatorTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private RequestValidator requestValidator;

	@Test
	public void testValidate_ValidUser() {
		UserSignUpRequestVO user = new UserSignUpRequestVO();
		user.setUsername("testuser");
		user.setEmail("testuser@example.com");

		when(userRepository.existsByUserName("testuser")).thenReturn(false);
		when(userRepository.existsByEmail("testuser@example.com")).thenReturn(false);

		requestValidator.validate(user);

		verify(userRepository, times(1)).existsByUserName("testuser");
		verify(userRepository, times(1)).existsByEmail("testuser@example.com");
	}

	@Test
	public void testValidate_DuplicateUsername() {
		UserSignUpRequestVO user = new UserSignUpRequestVO();
		user.setUsername("testuser");
		user.setEmail("testuser@example.com");

		when(userRepository.existsByUserName("testuser")).thenReturn(true);

		try {
			requestValidator.validate(user);
		} catch (DuplicateDataException e) {
			verify(userRepository, times(1)).existsByUserName("testuser");
		}
	}

	@Test
	public void testValidate_InvalidEmail() {
		UserSignUpRequestVO user = new UserSignUpRequestVO();
		user.setUsername("testuser");
		user.setEmail("invalidemail");

		when(userRepository.existsByUserName("testuser")).thenReturn(false);

		try {
			requestValidator.validate(user);
		} catch (InvalidRequestException e) {
			verify(userRepository, times(1)).existsByUserName("testuser");
		}
	}

	@Test
	public void testValidate_DuplicateEmail() {
		UserSignUpRequestVO user = new UserSignUpRequestVO();
		user.setUsername("testuser");
		user.setEmail("testuser@example.com");

		when(userRepository.existsByUserName("testuser")).thenReturn(false);
		when(userRepository.existsByEmail("testuser@example.com")).thenReturn(true);

		try {
			requestValidator.validate(user);
		} catch (DuplicateDataException e) {
			verify(userRepository, times(1)).existsByUserName("testuser");
			verify(userRepository, times(1)).existsByEmail("testuser@example.com");
		}
	}
}

package com.manushi.user.service.userdetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.manushi.user.repository.UserRepository;
import com.manushi.user.repository.entity.Users;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	@Mock
	private UserRepository userRepository;

	@Test
	public void testLoadUserByUsername_UserFound() {
		// Arrange
		Users user = new Users();
		user.setUserName("testuser");
		user.setPassword("password");

		when(userRepository.findByUserName("testuser")).thenReturn(user);

		// Act
		UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

		// Assert
		assertEquals("testuser", userDetails.getUsername());
		assertEquals("password", userDetails.getPassword());
	}

	@Test
	public void testLoadUserByUsername_UserNotFound() {
		// Arrange
		when(userRepository.findByUserName(anyString())).thenReturn(null);

		// Act and Assert
		assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername("nonexistentuser");
		});
	}
}

package com.manushi.user.repository.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UsersTest {

	@Test
	public void testGettersAndSetters() {
		Users users = new Users();
		users.setId(1L);
		users.setEmail("test@example.com");
		users.setUserName("testuser");
		users.setPassword("password");
		users.setName("Test User");

		assertEquals(1L, users.getId());
		assertEquals("test@example.com", users.getEmail());
		assertEquals("testuser", users.getUserName());
		assertEquals("password", users.getPassword());
		assertEquals("Test User", users.getName());
	}
}

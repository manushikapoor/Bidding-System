package com.manushi.bidding.repository.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsersTest {

	@InjectMocks
	private Users users;

	@Test
	public void setId() {
		users.setId(1L);
		assertEquals(1L, users.getId());
	}

	@Test
	public void setEmail() {
		users.setEmail("test@example.com");
		assertEquals("test@example.com", users.getEmail());
	}

	@Test
	public void setUserName() {
		users.setUserName("testUser");
		assertEquals("testUser", users.getUserName());
	}

	@Test
	public void setPassword() {
		users.setPassword("password123");
		assertEquals("password123", users.getPassword());
	}

	@Test
	public void setName() {
		users.setName("John Doe");
		assertEquals("John Doe", users.getName());
	}

	@Test
	public void setRoleId() {
		users.setRoleId(1L);
		assertEquals(1L, users.getRoleId());
	}

	@Test
	public void setVendorId() {
		users.setVendorId(2L);
		assertEquals(2L, users.getVendorId());
	}

	@Test
	public void builderTest() {
		Users user = Users.builder().id(1L).email("test@example.com").userName("testUser").password("password123").name("John Doe").roleId(1L)
				.vendorId(2L).build();

		assertEquals(1L, user.getId());
		assertEquals("test@example.com", user.getEmail());
		assertEquals("testUser", user.getUserName());
		assertEquals("password123", user.getPassword());
		assertEquals("John Doe", user.getName());
		assertEquals(1L, user.getRoleId());
		assertEquals(2L, user.getVendorId());
	}

	@Test
	public void equalsAndHashCodeTest() {
		Users user1 = Users.builder().id(1L).build();
		Users user2 = Users.builder().id(1L).build();
		Users user3 = Users.builder().id(2L).build();

		assertEquals(user1, user2);
		assertEquals(user1.hashCode(), user2.hashCode());
		assertNotEquals(user1, user3);
		assertNotEquals(user1.hashCode(), user3.hashCode());
	}

	@Test
	public void toStringTest() {
		// Arrange
		Users user = Users.builder().id(1L).email("test@example.com").userName("testUser").password("password123").name("John Doe").roleId(1L)
				.vendorId(2L).build();

		// Act & Assert
		assertEquals("Users(id=1, email=test@example.com, userName=testUser, password=password123, name=John Doe, roleId=1, vendorId=2)",
				user.toString());
	}
}

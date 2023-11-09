package com.manushi.user.repository.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

public class RolesTest {

	@Test
	public void testRolesEntity() {
		// Create a mock Roles object
		Roles roles = mock(Roles.class);

		// Define the expected values
		Long expectedRoleId = 1L;
		String expectedRoleName = "ROLE_USER";
		String expectedDescription = "User Role";

		// Set up the mock object to return the expected values
		when(roles.getRoleId()).thenReturn(expectedRoleId);
		when(roles.getRoleName()).thenReturn(expectedRoleName);
		when(roles.getDescription()).thenReturn(expectedDescription);

		// Verify that the entity getters return the expected values
		assertEquals(expectedRoleId, roles.getRoleId());
		assertEquals(expectedRoleName, roles.getRoleName());
		assertEquals(expectedDescription, roles.getDescription());
	}
}

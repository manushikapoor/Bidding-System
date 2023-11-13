package com.manushi.bidding.service.authorization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class AuthorizationHandlerServiceTest {

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthorizationHandlerService authorizationHandlerService;

    @Test
    public void testGetAuthorizationHeader() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Bearer Token123");

        // Act
        String authorizationHeader = authorizationHandlerService.getAuthorizationHeader(request);

        // Assert
        assertNotNull(authorizationHeader);
        assertEquals("Bearer Token123", authorizationHeader);
    }

    @Test
    public void testGetAuthorizationHeaderWithNullRequest() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authorizationHandlerService.getAuthorizationHeader(null));
        assertEquals("Authorization header cannot be null", exception.getMessage());
    }

    @Test
    public void testGetAuthorizationHeaderWithNullHeaderValue() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        String authorizationHeader = authorizationHandlerService.getAuthorizationHeader(request);

        // Assert
        assertNull(authorizationHeader);
    }
}

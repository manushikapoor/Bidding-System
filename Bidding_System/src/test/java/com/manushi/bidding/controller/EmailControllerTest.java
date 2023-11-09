package com.manushi.bidding.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.manushi.bidding.model.request.SendGridEmailMessage;
import com.manushi.bidding.service.email.SendGridEmailProducerImpl;
import com.manushi.bidding.service.products.ProductServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {

	@InjectMocks
	private EmailController emailController;

	@Mock
	private SendGridEmailProducerImpl emailProducer;

	@Mock
	private ProductServiceImpl productServiceImpl;

	@Test
	public void testSendEmailToWinners() {
		// Create a mock HttpServletRequest
		HttpServletRequest request = mock(HttpServletRequest.class);

		// Call the controller method
		ResponseEntity<String> response = emailController.sendEmailToWinners(request);

		// Assert the HTTP status code
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		// Assert the response body
		assertEquals("Email sent to all the winners.", response.getBody());
	}

	@Test
	public void testSendEmail() {
		// Create a mock HttpServletRequest
		HttpServletRequest request = mock(HttpServletRequest.class);

		// Create a sample SendGridEmailMessage
		SendGridEmailMessage emailMessage = new SendGridEmailMessage();
		emailMessage.setTo("to");
		emailMessage.setContent("content");
		emailMessage.setSubject("sub");

		// Call the controller method
		ResponseEntity<String> response = emailController.sendEmail(request, emailMessage);

		// Assert the HTTP status code
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		// Assert the response body
		assertEquals("Email sent to SendGrid.", response.getBody());
	}
}
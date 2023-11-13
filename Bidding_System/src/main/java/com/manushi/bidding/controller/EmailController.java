package com.manushi.bidding.controller;

import static com.manushi.bidding.util.Constants.BIDS;
import static com.manushi.bidding.util.Constants.NOTIFY;
import static com.manushi.bidding.util.Constants.SEND_MAIL;
import static com.manushi.bidding.util.Constants.V1;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manushi.bidding.model.request.SendGridEmailMessage;
import com.manushi.bidding.service.email.EmailProducerImpl;
import com.manushi.bidding.service.products.ProductServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(V1 + BIDS)
@AllArgsConstructor
public class EmailController {

	@Autowired
	private final EmailProducerImpl emailProducer;

	@Autowired
	private final ProductServiceImpl productServiceImpl;

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'admin')== true ")
	@PostMapping(NOTIFY)
	public ResponseEntity<String> sendEmailToWinners(HttpServletRequest request) {
		productServiceImpl.sendMailToWinners(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email sent to all the winners.");
	}

	@PreAuthorize("@tokenAuthorization.validateTokenAndCheckRole(@authorizationHandlerService.getAuthorizationHeader(#request), 'admin')== true")
	@PostMapping(SEND_MAIL)
	public ResponseEntity<String> sendEmail(HttpServletRequest request, @Valid @RequestBody SendGridEmailMessage emailMessage) {
		emailProducer.sendEmail(emailMessage);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email sent to SendGrid.");
	}
}

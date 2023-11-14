package com.manushi.bidding.service.email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.manushi.bidding.model.request.QueueEmailMessage;

@ExtendWith(MockitoExtension.class)
class EmailProducerImplTest {

	@Mock
	private RabbitTemplate rabbitTemplateMock;

	@Mock
	private DirectExchange exchangeMock;

	@InjectMocks
	private EmailProducerImpl emailProducer;

	@Test
	void sendEmail_Success() {
		// Arrange
		QueueEmailMessage emailMessage = new QueueEmailMessage();
		emailMessage.setContent("content");
		emailMessage.setSubject("subject");
		emailMessage.setTo("to");
		// Act
		emailProducer.sendEmail(emailMessage);

	}

}

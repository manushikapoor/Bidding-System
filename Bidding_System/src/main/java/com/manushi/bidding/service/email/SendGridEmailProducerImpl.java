package com.manushi.bidding.service.email;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.manushi.bidding.exceptions.RabbitMqException;
import com.manushi.bidding.model.request.SendGridEmailMessage;

@Service
public class SendGridEmailProducerImpl implements SendGridEmailProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private DirectExchange exchange;

	@Value("${email.routing-key}")
	private String routingKey;

	@Override
	public void sendEmail(SendGridEmailMessage emailMessage) {
		try {
			rabbitTemplate.convertAndSend(exchange.getName(), routingKey, emailMessage);
		} catch (Exception ex) {
			throw new RabbitMqException("RMQ Exception occurred");
		}
	}
}

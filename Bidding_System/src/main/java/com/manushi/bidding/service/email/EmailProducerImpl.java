package com.manushi.bidding.service.email;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.manushi.bidding.exceptions.RabbitMqException;
import com.manushi.bidding.model.request.QueueEmailMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailProducerImpl implements EmailProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private DirectExchange exchange;

	@Value("${email.routing-key}")
	private String routingKey;

	@Override
	public void sendEmail(QueueEmailMessage emailMessage) {
		try {
			rabbitTemplate.convertAndSend(exchange.getName(), routingKey, emailMessage);
			log.info("message successfully sent to queue - {}", emailMessage);
			log.debug("Routing key - {} and exchange - {}", routingKey, exchange.getName());
		} catch (Exception ex) {
			throw new RabbitMqException("RMQ Exception occurred");
		}
	}
}

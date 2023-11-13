package com.manushi.bidding.service.email;

import com.manushi.bidding.model.request.SendGridEmailMessage;

public interface EmailProducer {

	void sendEmail(SendGridEmailMessage emailMessage);

}

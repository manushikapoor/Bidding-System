package com.manushi.bidding.service.email;

import com.manushi.bidding.model.request.QueueEmailMessage;

public interface EmailProducer {

	void sendEmail(QueueEmailMessage emailMessage);

}

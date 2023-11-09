package com.manushi.bidding.model.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendGridEmailMessage implements Serializable {

	@NotBlank(message = "to is required")
	private String to;

	@NotBlank(message = "subject is required")
	private String subject;

	@NotBlank(message = "content is required")
	private String content;

}

package com.manushi.bidding.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonTypeName("Error")
public class ErrorVO {

	@JsonProperty("error_code")
	private String errorCode;

	@JsonProperty("error")
	private String error;

	@JsonProperty("message")
	private String message;

}

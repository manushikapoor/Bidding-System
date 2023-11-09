package com.manushi.user.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSignInRequestVO {

	@JsonProperty("username")
	@NotBlank(message = "Username is required")
	private String username;

	@JsonProperty("password")
	@NotBlank(message = "Password is required")
	private String password;

}

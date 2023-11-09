package com.manushi.user.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSignUpRequestVO {

	@JsonProperty("email")
	@NotBlank(message = "Email is required")
	private String email;

	@JsonProperty("username")
	@NotBlank(message = "Username is required")
	private String username;

	@JsonProperty("name")
	@NotBlank(message = "Name is required")
	private String name;

	@JsonProperty("password")
	@NotBlank(message = "Password is required")
	private String password;

	@JsonProperty("role")
	@NotBlank(message = "Role is required")
	private String role;

	@JsonProperty("vendor")
	private String vendor;
}

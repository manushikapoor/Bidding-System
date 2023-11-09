package com.manushi.user.util;

import static com.manushi.user.util.Constants.EMAIL_VALIDATION_REGEX;
import static com.manushi.user.util.Constants.ERROR_EMAIL_EXISTS;
import static com.manushi.user.util.Constants.ERROR_MSG_INVALID_EMAIL;
import static com.manushi.user.util.Constants.ERROR_USERNAME_TAKEN;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manushi.user.exceptions.DuplicateDataException;
import com.manushi.user.exceptions.InvalidRequestException;
import com.manushi.user.model.request.UserSignUpRequestVO;
import com.manushi.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestValidator {

	@Autowired
	UserRepository userRepository;

	private static final Pattern EMAIL_VALIDATOR_PATTERN = Pattern.compile(EMAIL_VALIDATION_REGEX);

	public void validate(UserSignUpRequestVO user) {
		if (userRepository.existsByUserName(user.getUsername())) {
			throw new DuplicateDataException(ERROR_USERNAME_TAKEN);
		}
		if (!isValidMailAddress(user.getEmail())) {
			throw new InvalidRequestException(ERROR_MSG_INVALID_EMAIL);
		}
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new DuplicateDataException(ERROR_EMAIL_EXISTS);
		}
		log.info("User successfully validated with username - {} ", user.getUsername());
	}

	private boolean isValidMailAddress(String mailAddress) {
		return mailAddress.isEmpty() ? false : EMAIL_VALIDATOR_PATTERN.matcher(mailAddress).matches();
	}

}

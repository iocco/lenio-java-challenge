package com.leniolabs.challenge;

import com.leniolabs.challenge.controller.AccountController;
import com.leniolabs.challenge.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ChallengeApplicationTests {

	@Autowired
	private AccountController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void createAndGetCorporateFee() {
		ResponseEntity<String> createResponse = controller.createAccount(
				new Account("testId", "name", "lastName", "corporate", 1.0));

		assertThat(createResponse.getStatusCodeValue()).isEqualTo(200);
		assertThat(createResponse.getBody()).isEqualTo("testId");

		ResponseEntity<Double> feeResponse = controller.calculateFee("testId");

		assertThat(feeResponse.getStatusCodeValue()).isEqualTo(200);
		assertThat(feeResponse.getBody()).isEqualTo(0.05);
	}

	@Test
	public void getFeeNotFoundAccount() {
		ResponseEntity<Double> feeResponse = controller.calculateFee("otherId");

		assertThat(feeResponse.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void createOtherTypeNotFound() {
		ResponseEntity<String> createResponse = controller.createAccount(
				new Account("notCorporate", "name", "lastName", "other", 1.0));

		assertThat(createResponse.getStatusCodeValue()).isEqualTo(200);
		assertThat(createResponse.getBody()).isEqualTo("notCorporate");

		ResponseEntity<Double> feeResponse = controller.calculateFee("notCorporate");

		assertThat(feeResponse.getStatusCodeValue()).isEqualTo(404);
	}

}

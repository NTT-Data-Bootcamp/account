package com.bootcamp.java.account;

//import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Assertions;

import com.bootcamp.java.account.domain.Account;
import com.bootcamp.java.account.web.mapper.AccountMapper;

@SpringBootTest(classes = { AccountMapper.class })
public class AccountModelTest {

	/*
	@Test
	void validateToString() throws Exception {
		// given
		Product client = Product.builder().id("112345523").name("CARLOS").lastName("JUAREZ SALAZAR").clientType("PERSONNEL").build();

		// when
		assertNotNull(client.toString());
	}

	@Test
	void validateHashCode() throws Exception {
		// given
		Product client = Product.builder().id("112345523").name("CARLOS").lastName("JUAREZ SALAZAR").clientType("PERSONNEL").build();

		// when
		assertNotNull(client.hashCode());
	}
	
	@Test
	void validateFieldClienType() throws Exception {
		// given
		Product client = Product.builder().id("112345523").name("CARLOS").lastName("JUAREZ SALAZAR").clientType("PERSONNEL").build();
		
		assertEquals(client.clientType, "BUSINESS");
		
	}
	*/
	

}

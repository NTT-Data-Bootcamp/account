package com.bootcamp.java.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bootcamp.java.account.domain.Account;
import com.bootcamp.java.account.repository.AccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient

public class AccountControllerTest {
	/*
	@Autowired
	private WebTestClient wtProduct;

	@MockBean
	ProductRepository clientRepository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAll() {
		// given
		Product client = Product.builder().id("112345523").identityDocumentType("DNI").identityDocumentNumber("123454").name("Lita")
				.businessName("").lastName("Lara").email("demo@entel.pe").clientType("PERSONNEL")
				.phoneNumber("121323").idProductProfile("PB").build();
		// when
		when(clientRepository.findAll()).thenReturn(Flux.just(client));

		List<Product> accountResponseList = wtProduct.get().uri(uriBuilder -> uriBuilder.path("/v1/client").build())
				.exchange().expectStatus().isOk().expectBodyList(Product.class).returnResult().getResponseBody();

		// THEN
		assertNotNull(accountResponseList);
	}

	@Test
	void getById() {
		// given
		Product customer = Product.builder().id("112345523").identityDocumentNumber("123454").name("Daniel")
				.businessName("").lastName("Zárate").email("demo@entel.pe").clientType("PERSONNEL")
				.phoneNumber("121323").build();

		// when
		when(clientRepository.findById("112345523")).thenReturn(Mono.just(customer));
		
		// WHEN
		List<Product> accountResponseList = wtProduct.get()
				.uri(uriBuilder -> uriBuilder.path("/v1/client/{id}").build("112345523")).exchange().expectStatus()
				.isOk().expectBodyList(Product.class).returnResult().getResponseBody();

		// THEN
		assertNotNull(accountResponseList);
	}

	@Test
	void create() {
		// given
		Product customer = Product.builder().id("112345523").identityDocumentType("DNI").identityDocumentNumber("123454").name("Daniela")
				.businessName("").lastName("Mendoza").email("demo@entel.pe").clientType("PERSONNEL")
				.phoneNumber("121323").idProductProfile("PB").build();

		// when
		when(clientRepository.save(any())).thenReturn(Mono.just(customer));

		// then
		wtProduct.post().uri(uriBuilder -> uriBuilder.path("/v1/client/").build()).bodyValue(customer).exchange()
				.expectStatus().isCreated().expectBody(Product.class).returnResult().getResponseBody();
	}

	@Test
	void updateById() {
		// given
		Product customer = Product.builder().id("112345523").identityDocumentType("DNI").identityDocumentNumber("123454").name("Daniela")
				.businessName("").lastName("Mendoza").email("demo@entel.pe").clientType("PERSONNEL")
				.phoneNumber("121323").idProductProfile("PB").build();

		// when
		when(clientRepository.save(any())).thenReturn(Mono.just(customer));

		when(clientRepository.findById("112345523")).thenReturn(Mono.just(customer));

		// then
		wtProduct.put().uri(uriBuilder -> uriBuilder.path("/v1/client/{id}").build("112345523")).bodyValue(customer)
				.exchange().expectStatus().isCreated().expectBody(Product.class).returnResult().getResponseBody();
	}

	@Test
	void deleteById() {
		// given
		Product client = Product.builder().id("112345523").identityDocumentNumber("123454").name("Daniela")
				.businessName("").lastName("Mendoza").email("demo@entel.pe").clientType("PERSONNEL")
				.phoneNumber("121323").build();

		// when
		when(clientRepository.findById("112345523")).thenReturn(Mono.just(client));
		when(clientRepository.delete(client)).thenReturn(Mono.empty());

		// then
		wtProduct.delete().uri(uriBuilder -> uriBuilder.path("/v1/client/{id}").build("112345523")).exchange()
				.expectStatus().isOk();
	}
	*/
}

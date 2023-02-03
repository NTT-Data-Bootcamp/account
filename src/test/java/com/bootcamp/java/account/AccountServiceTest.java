package com.bootcamp.java.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bootcamp.java.account.domain.Account;
import com.bootcamp.java.account.repository.AccountRepository;
import com.bootcamp.java.account.service.AccountService;
import com.bootcamp.java.account.web.mapper.AccountMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = { AccountMapper.class })
public class AccountServiceTest {
	/*
	@MockBean
	ProductRepository clientRepository;
	
	@MockBean
	ProductMapper clientMapper;
	
	@MockBean
	ProductProfileRepository clientProfileRepository;
	
	@MockBean
	ProductProfileService clientProfileService;
	
	@InjectMocks
	ProductService clientService;
	

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void create() throws Exception {
		// given
		Product client = Product.builder().id("111111111").identityDocumentType("DNI").identityDocumentNumber("41526385").name("LUIS ANDRES").lastName("DEZA CARPIO").clientType("PERSONNEL").idProductProfile("PB").build();

		// when
		when(clientRepository.save(any())).thenReturn(Mono.just(client));

		// then
		StepVerifier.create(clientService.create(client)).assertNext(clientResponse -> {
			assertNotNull(clientResponse);
			assertEquals("111111111", clientResponse.getId());
		}).verifyComplete();
	}

	@Test
	void update() throws Exception {
		// given
		Product clientRequest = Product.builder().id("111111111").name("Alice").lastName("Bazan").clientType("PERSONNEL").build();
		Product clientResponse = Product.builder().id("111111111").name("Betty").lastName("Cabrera").clientType("PERSONNEL").build();

		// when
		when(clientRepository.save(any())).thenReturn(Mono.just(clientResponse));
		when(clientRepository.findById("111111111")).thenReturn(Mono.just(clientResponse));

		// then
		StepVerifier.create(clientService.update("111111111", clientRequest)).assertNext(clientResponse2 -> {
			assertNotNull(clientResponse2);
			assertEquals("111111111", clientResponse2.getId());
		}).verifyComplete();

	}

	@Test
	void findAll() throws Exception {
		// given
		Product client = Product.builder().id("111111111").name("Alice").lastName("Bazan").clientType("PERSONNEL").build();

		// when
		when(clientRepository.findAll()).thenReturn(Flux.just(client));

		// then
		StepVerifier.create(clientService.findAll()).expectNext(client).verifyComplete();

	}

	@Test
	void findById() throws Exception {
		// given
		Product client = Product.builder().id("111111111").name("Alice").lastName("Bazan").clientType("PERSONNEL").build();

		// when
		when(clientRepository.findById("111111111")).thenReturn(Mono.just(client));

		// then
		StepVerifier.create(clientService.findById("111111111")).expectNext(client).verifyComplete();
	}

	@Test
	void deleteById() throws Exception {
		// given
		Product client = Product.builder().id("111111111").name("Alice").lastName("Bazan").clientType("PERSONNEL").build();

		// when
		when(clientRepository.findById("111111111")).thenReturn(Mono.just(client));
		when(clientRepository.delete(client)).thenReturn(Mono.empty());
		
		// then
		StepVerifier.create(clientService.delete("111111111")).expectNext(client).verifyComplete();
	}
	*/
}

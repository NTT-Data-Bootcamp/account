package com.bootcamp.java.account.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.java.account.domain.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String>{
	Mono<Integer> countByIdClient(String idClient);
	Mono<Integer> countByIdClientAndIdProduct(String idClient, String idProduct);
    Mono<Account> findTopByAccountNumber(String accountNumber);
    
}

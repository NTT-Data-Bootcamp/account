package com.bootcamp.java.account.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.java.account.domain.Account;
import com.bootcamp.java.account.service.AccountService;
import com.bootcamp.java.account.service.exception.AccountModel;
import com.bootcamp.java.account.web.mapper.AccountMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/account")
public class AccountController {
	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountMapper accountMapper;
	
	
	@Operation(
            summary = "Get a list of accounts",
            description = "Get a list of accounts registered in the system",
            responses = {
            		@ApiResponse(responseCode = "200",
                    description = "The response for the account request")
            }
    )
	@GetMapping
	public Mono<ResponseEntity<Flux<AccountModel>>> getAll(){
		log.info("getAll executed");
		return Mono.just(ResponseEntity.ok()
			.body(accountService.findAll()
					.map(account -> accountMapper.entityToModel(account))));
	}
	
	
	@Operation(summary = "Funcionalidad de consulta de un account por ID")
	@ApiResponses(value= {
		@ApiResponse(responseCode = "200", description = "account found succesully.",
				content = { @Content(mediaType = "application/json",
				schema = @Schema(implementation = Account.class)) }),
		@ApiResponse(responseCode = "400", description = "ID not valid.",
			content = @Content),
		@ApiResponse(responseCode = "404", description = "account not found.",
			content = @Content)
	})
	@GetMapping("/{id}")
	public Mono<ResponseEntity<AccountModel>> getById(@PathVariable String id){
		log.info("getById executed {}", id);
		Mono<Account> response = accountService.findById(id);
		return response
				.map(account -> accountMapper.entityToModel(account))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
		
		//        .switchIfEmpty(Mono.error(new DataNotFoundException("The data you seek is not here."))); // NO FUNCIONA
		/*
		 return serverRequest.bodyToMono(RequestDTO.class)
                .map((request) -> searchLocations(request.searchFields, request.pageToken))
                .flatMap( t -> ServerResponse
                        .ok()
                        .body(t, ResponseDTO.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build())
                ;
		 * */
	}
	
	@GetMapping("/GetByAccountNumber/{accountNumber}")
	public Mono<ResponseEntity<AccountModel>> getByAccountNumber(@PathVariable String accountNumber){
		log.info("getByAccountNumber executed {}", accountNumber);
		Mono<Account> response = accountService.findTopByAccountNumber(accountNumber);
		return response
				.map(account -> accountMapper.entityToModel(account))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
				
		
		//        .switchIfEmpty(Mono.error(new DataNotFoundException("The data you seek is not here."))); // NO FUNCIONA
		/*
		 return serverRequest.bodyToMono(RequestDTO.class)
                .map((request) -> searchLocations(request.searchFields, request.pageToken))
                .flatMap( t -> ServerResponse
                        .ok()
                        .body(t, ResponseDTO.class)
                )
                .switchIfEmpty(ServerResponse.notFound().build())
                ;
		 * */
	}
	
	@PostMapping
	public Mono<ResponseEntity<AccountModel>> create(@Valid @RequestBody AccountModel request){
		log.info("create executed {}", request);
		return accountService.create(accountMapper.modelToEntity(request))
				.map(account -> accountMapper.entityToModel(account))
				.flatMap(c ->
					Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
							port, "account", c.getId())))
							.body(c)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<AccountModel>> updateById(@PathVariable String id, @Valid @RequestBody AccountModel request){
		log.info("updateById executed {}:{}", id, request);
		return accountService.update(id, accountMapper.modelToEntity(request))
				.map(account -> accountMapper.entityToModel(account))
				.flatMap(c ->
					Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
						port, "account", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
		log.info("deleteById executed {}", id);
		return accountService.delete(id)
				.map( r -> ResponseEntity.ok().<Void>build())
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/countAccountdByIdClient/{idCliente}")
    public Mono<ResponseEntity<Integer>> countAccountByIdClient(@PathVariable String idCliente){
        log.info("countAccountByIdClient executed {}", idCliente);

        Mono<Integer> response = accountService.countByIdClient(idCliente);
        return response
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	

	/*
	@GetMapping("getByIdProductTypeAndActive/{idProductType}")
	public Mono<ResponseEntity<Flux<AccountModel>>> getByIdProductTypeAndActive(@PathVariable String idProductType){
		log.info("getByIdProductTypeAndActive executed in Controller {}", idProductType);
		return Mono.just(ResponseEntity.ok()
				.body(productService.findByIdProductTypeAndActive(idProductType, true)
						.map(product -> productMapper.entityToModel(product))));
		/*
		 Flux<Product> response = productService.findByIdProductTypeAndActive(idProductType, true);		
		 return response
				.map(product -> productMapper.entityToModel(product))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
		
		
	}*/
}

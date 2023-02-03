package com.bootcamp.java.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.java.account.domain.Account;
import com.bootcamp.java.account.repository.AccountRepository;
import com.bootcamp.java.account.web.mapper.AccountMapper;
import com.bootcamp.java.account.web.model.ClientModel;
import com.bootcamp.java.account.web.model.ProductModel;
import com.bootcamp.java.account.web.model.ProductParameterModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountMapper accountMapper;
	
	private WebClient getWebClientClient(){
        log.debug("getWebClientClient executed");
        return WebClient.builder()
                .baseUrl("http://localhost:9050/v1/client")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
	
	private WebClient getWebClientProduct(){
        log.debug("getWebClientProduct executed");
        return WebClient.builder()
                .baseUrl("http://localhost:9051/v1/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
	
	private WebClient getWebClientProductParameter(){
        log.debug("getWebClientProductParameter executed");
        return WebClient.builder()
                .baseUrl("http://localhost:9051/v1/productparameter")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
	
	private WebClient getWebClientCredit(){
        log.debug("getWebClientCredit executed");
        return WebClient.builder()
                .baseUrl("http://localhost:9053/v1/creditcard")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
	
	public Flux<Account> findAll(){
		log.debug("findAll executed");
		return accountRepository.findAll();
	}

	public Mono<Account> findById(String accountId){
		log.debug("findById executed {}", accountId);
		return accountRepository.findById(accountId);
	}
	
	public Mono<Account> findTopByAccountNumber(String accountNumber){
		log.debug("findTopByAccountNumber executed {}", accountNumber);
		return accountRepository.findTopByAccountNumber(accountNumber);
	}
	
	
	public Mono<Account> create(Account account){
		log.debug("create service executed {}", account);		
		//return accountRepository.save(account);
		/*
		return getClientById(account.getIdClient())
				.switchIfEmpty(Mono.error(new Exception("Client does not exist")))
				.flatMap(clientModel -> {
					return getProductById(account.getIdProduct())
							.switchIfEmpty(Mono.error(new Exception("Product does not exist")))
							.flatMap(productModel -> {
								return validateProductParameter(account.getIdProduct(), clientModel.getClientType(), clientModel.getIdClientProfile())
									//.switchIfEmpty(Mono.error(new Exception("ProductParameter does not exist para 3")))
										.switchIfEmpty(validateProductParameterWithoutClientProfile(account.getIdProduct(), clientModel.getClientType()))
										.switchIfEmpty(Mono.error(new Exception("ProductParameter does not exist para 2")))
										.flatMap(productParameterModel -> accountRepository.save(account));
									
							});
							
				});
		
		*/
		return getClientById(account.getIdClient())
				.switchIfEmpty(Mono.error(new Exception("Client does not exist")))
				.flatMap(clientModel -> 
					getProductById(account.getIdProduct())
					.switchIfEmpty(Mono.error(new Exception("Product does not exist")))
					.flatMap(productModel -> validateProductParameter(account.getIdProduct(), clientModel.getClientType(), clientModel.getIdClientProfile()))
					//.switchIfEmpty(Mono.error(new Exception("ProductParameter does not exist para 3")))
					.switchIfEmpty(validateProductParameterWithoutClientProfile(account.getIdProduct(), clientModel.getClientType()))
					.switchIfEmpty(Mono.error(new Exception("ProductParameter does not exist para 2")))
					.flatMap(productParameterModel -> {
						Mono<Boolean> isOKQuantityProduct, isOKQuantityOwners, isOKQuantitySigners, isOKQuantityCreditCards;
						
						// verificar máximo número de producto account
						Integer maxQuantityProduct = productParameterModel.getMaxQuantityProduct();
						Mono<Integer> quantityAccountByClientProduct = accountRepository.countByIdClientAndIdProduct(account.getIdClient(), account.getIdProduct());
						isOKQuantityProduct = quantityAccountByClientProduct.flatMap(quantity -> quantity < maxQuantityProduct ? Mono.just(true) : Mono.just(false) );
						
						// verificar minimo numero de titulares (owners)
						Integer minQuantityOwners = productParameterModel.getMinimumOwners();						
						int quantityOwners = account.getOwners() == null || account.getOwners().isEmpty() ? 0 : account.getOwners().size();
						isOKQuantityOwners = quantityOwners >= minQuantityOwners ? Mono.just(true) : Mono.just(false);				        
				        
						// verificar minimo numero de firmantes (signers)
						Integer minQuantitySigners = productParameterModel.getMinimumSigners();						
						int quantitySigners = account.getSigners() == null || account.getSigners().isEmpty() ? 0 : account.getSigners().size();
						isOKQuantitySigners = quantitySigners >= minQuantitySigners ? Mono.just(true) : Mono.just(false);
						
						// verificar card required
						Boolean isCardRequired = productParameterModel.getCardRequired();	
						//Mono<Integer> quantityCreditCards = getWebClientCredit().get().uri("creditcard/countCreditCardByIdClient/" + account.getIdClient() ).retrieve().bodyToMono(Integer.class);
						isOKQuantityCreditCards = !isCardRequired  ? Mono.just(true) : getWebClientCredit().get().uri("creditcard/countCreditCardByIdClient/" + account.getIdClient() ).retrieve().bodyToMono(Integer.class).flatMap(quantity -> quantity > 0 ? Mono.just(true) : Mono.just(false));
						
						isOKQuantityProduct.flatMap(value1 -> {
							if(!value1)	
								return Mono.error(new Exception("Quantity Product >= Maximun quantity of Account permitted"));							
							
							isOKQuantityOwners.flatMap(value2 -> {
								if(!value2)	
									return Mono.error(new Exception(productParameterModel.getMinimumOwners().toString() + " owners are required"));
								isOKQuantitySigners.flatMap(value3 -> {
									if(!value3)	
										return Mono.error(new Exception(productParameterModel.getMinimumSigners().toString() + " signers are required"));
									isOKQuantityCreditCards.flatMap(value4 -> {
										if(!value4)	
											return Mono.error(new Exception("credit card is required"));
										return accountRepository.save(account);						
									});	
									return null;
								});	
								return null;
							});
							return null;	
						}); //.flatMap(Mono.just(account));						
						//return accountRepository.save(account);					
						return null;
					})
				);
				
		
		/*
				.flatMap(productModel -> validateProductParameter(account.getIdProduct(), clientModel.getClientType(), clientModel.getIdClientProfile())
                .flatMap(productParameterModel -> {
                    Mono<Boolean> maxAccountValidated = validateMaxAccount(account.getClient(), account.getProductCode(), productParameterModel.getMaxProduct());
                    Mono<Boolean> holdersValidated = validateHolderRequired(account.getHolder(), productParameterModel.getMinimumHolder());
                    Mono<Boolean> signersValidated = validateSignerRequired(account.getSigner(), productParameterModel.getMinimumSigner());
                    Mono<Boolean> accountRequired = validateAccount(productParameterModel.getAccountRequired(), account.getClient());
                    Mono<Boolean> cardRequired = validateCard(productParameterModel.getCardRequired(), account.getClient());

                    return Mono.zip(maxAccountValidated, holdersValidated, signersValidated, accountRequired, cardRequired)
                            .flatMap(objects -> !objects.getT1() ? Mono.error(new Exception("Max. Account")) : !objects.getT2()
                                    ? Mono.error(new Exception(productParameterModel.getMinimumHolder().toString() + " Holder Required")) : !objects.getT3() ? Mono.error(new Exception(productParameterModel.getMinimumSigner().toString() + " Signer Required")) : !objects.getT4()
                                    ? Mono.error(new Exception("Account created is required")) : !objects.getT5() ? Mono.error(new Exception("Card created is required")) :
                                    accountRepository.save(account));
                }));
                */
	}
	
	
	
	public Mono<Account> update(String accountId, Account account){
		log.debug("update executed {}:{}", accountId, account);
		return accountRepository.findById(accountId)
		.flatMap(dbAccount -> {
			accountMapper.update(dbAccount, account);
			return accountRepository.save(dbAccount);
		});		
	}
	
	public Mono<Account> updateByAccountNumber(String accountNumber, Account account) {
        log.debug("updateByAccountNumber executed {}", account);
        return accountRepository.findTopByAccountNumber(accountNumber)
                .flatMap(dbAccount -> {
                    accountMapper.update(dbAccount, account);
                    return accountRepository.save(dbAccount);
                });
    }


	public Mono<Account> delete(String accountId){
		log.debug("delete executed {}", accountId);
		return accountRepository.findById(accountId)
		.flatMap(existingAccount -> accountRepository.delete(existingAccount)
		.then(Mono.just(existingAccount)));
	}
	
	public Mono<Integer> countByIdClient(String idCliente){
		log.debug("findById executed {}", idCliente);
		return accountRepository.countByIdClient(idCliente);
	}

	/*
	public Flux<Product> findByIdProductTypeAndActive(String idProductType, Boolean active){
		log.debug("findByIdProductTypeAndActive executed in service {} estado {}", idProductType, active);
		return productRepository.findByIdProductTypeAndActive(idProductType, active);
		
	}*/

	/////////// METODOS COMPLEMENTARIOS DE LOGICA DEL NEGOCIO ///////////////////////////////////
	private Mono<ClientModel> getClientById(String idClient){
        log.debug("getClientById executed {}", idClient);
        /*return getClientByDocument(client)
                .switchIfEmpty(Mono.error(new InvalidClientException()))
                .flatMap(Mono::just);*/
        // Se llama al EndPoint  getById de microservicio Client
        return getWebClientClient().get().uri("/" + idClient).retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new Exception("Client does not exist"))).bodyToMono(ClientModel.class);
    }	
	
	private Mono<ProductModel> getProductById(String idProduct){
        log.debug("getProductById executed {}", idProduct);
        
        // Se llama al EndPoint  getById de microservicio Product
        return getWebClientProduct().get().uri("/" + idProduct).retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new Exception("Product does not exist"))).bodyToMono(ProductModel.class);
    }
	
	private Mono<ProductParameterModel> validateProductParameter(String idProduct, String clientType, String idClientProfile){
		log.debug("validateProductAndGetProductParameter executed idProduct = {} - clientType = {} - idProfile = {}", idProduct, clientType, idClientProfile);
		// Se llama al EndPoint  getByIdProductAndClientTypeAndIdClientProfileAndActive de microservicio Product -> ProductParameter
		Mono<ProductParameterModel> ppm = getWebClientProductParameter().get().uri("/getByIdProductAndClientTypeAndIdClientProfileAndActive/" + idProduct + "/" + clientType + "/" + idClientProfile).retrieve()
                //.onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new Exception("ProductParameter does not exists for product = " + idProduct + ", clientType = " + clientType + ", idClientProfile = " + idClientProfile)))                
				//.switchIfEmpty()
				.bodyToMono(ProductParameterModel.class);
		
		return ppm;
		/*
				 .flatMap(ppmresult -> {
					 return getWebClientProductParameter().get().uri("/getByIdProductAndClientTypeAndActiveWithoutClientProfile/" + idProduct + "/" + clientType ).retrieve()
				                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new Exception("ProductParameter does not exists for product = " + idProduct + ", clientType = " + clientType)))                
				                .bodyToMono(ProductParameterModel.class);
				 })
				 .flatMap(Mono::just);
			*/
		
		 // getByIdProductAndClientTypeAndActiveWithoutClientProfile
		 
		/*
		return getParameterByCodeAndTypeAndProfile(code, type, profile)
                .switchIfEmpty(Mono.error(new Exception("Product, client type and client profile not configured")))
                .flatMap(Mono::just);
		*/
    }

		
	private Mono<ProductParameterModel> validateProductParameterWithoutClientProfile(String idProduct, String clientType){
		log.debug("validateProductAndGetProductParameter executed idProduct = {} - clientType = {}", idProduct, clientType);
		// Se llama al EndPoint  getByIdProductAndClientTypeAndIdClientProfileAndActive de microservicio Product -> ProductParameter
			
		return getWebClientProductParameter().get().uri("/getByIdProductAndClientTypeAndActiveWithoutClientProfile/" + idProduct + "/" + clientType ).retrieve()
				                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new Exception("ProductParameter does not exists for product = " + idProduct + ", clientType = " + clientType + " (without profile)")))                
				                .bodyToMono(ProductParameterModel.class);
    }

} 

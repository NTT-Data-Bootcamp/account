package com.bootcamp.java.account.service.exception;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountModel {
	
	@JsonIgnore
	private String id;	
	
	@NotBlank(message = "IdProduct cannot be null or empty")
	private String idProduct;
	
	@NotBlank(message = "idClient cannot be null or empty")
	private String idClient;
		
	@NotBlank(message = "accountNumber cannot be null or empty")
    private String accountNumber;
	
	
	private LocalDate openingDate;
	
	private Float availableBalance;
	
	private List<String> owners;
    private List<String> signers;
	
	private LocalDate updateDate;
    		
	@NotNull
    private Boolean active;
}

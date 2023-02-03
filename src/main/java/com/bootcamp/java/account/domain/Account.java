
package com.bootcamp.java.account.domain;


import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
//@EqualsAndHashCode(of = { "accountNumber" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "account")
public class Account {
	@Id
	private String id;
		
	@NotNull
	private String idProduct;
	
	@NotNull
	private String idClient;
		
	@NotNull
    @Indexed(unique = true)
    private String accountNumber;
	
	
	private LocalDate openingDate;
	
	private LocalDate updateDate;
	
	@NotNull
	private Float availableBalance;
	
	private List<String> owners;
    private List<String> signers;
	
    
	@NotNull
    private Boolean active;
}

package com.bootcamp.java.account.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
//@EqualsAndHashCode(of = { "idClientProfile" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "product-parameter")
public class ProductParameter {

	@Id
	private String id;
	
	
	@NotNull
	private String idProduct;
	@NotNull
	private String clientType;
	
	private String idClientProfile;
	
	
	private Integer maxQuantityProduct;
    
	// parametros de cuentas
	private Float maintanceCommissionAccount;    
    private Integer minimumOwners;
    private Integer minimumSigners;
    private Float averageMinimumAmountInMonth;
    private Boolean cardRequired;
    
    
    // parametros de transacciones    
    private Integer maxTransaccFree;
    private Float commissionByTransaccExtra;
    private Integer maxMovementsInMonth; //account
    private Integer dayMovementInMonth; //account
    
    // parametros de credits
    private Boolean accountRequired;
    
    private Boolean active;
    
    
}

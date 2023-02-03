package com.bootcamp.java.account.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nonapi.io.github.classgraph.json.Id;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = { "name" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "product-type")
public class ProductType {
	@Id
    private String id;
	
    @NotNull
    @Indexed(unique = true)
    private String name;   
    
    @NotNull
    private Boolean active;
}

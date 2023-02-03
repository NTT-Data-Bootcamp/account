package com.bootcamp.java.account.web.model;


import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeModel {

    private String id;
    @NotNull
    private String name;
    @NotNull
    private Boolean active;
}

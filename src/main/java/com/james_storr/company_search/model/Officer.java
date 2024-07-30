package com.james_storr.company_search.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class Officer {
    private String name;
    private String officer_role;
    private String appointed_on;
    private Address address;
    @JsonInclude(JsonInclude.Include.NON_NULL) 
    private String resigned_on;
}

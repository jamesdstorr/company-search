package com.james_storr.company_search.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Data;

@Data
public class Company {
    @Id
    @JsonProperty("company_number")
    private String companyNumber; 
    @JsonProperty("company_type") 
    private String companyType;
    @JsonProperty("title")
    private String title;
    @JsonProperty("company_status")
    private String companyStatus;
    @JsonProperty(value = "date_of_creation")
    private String dateOfCreation;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("officers") 
    private List<Officer> officers;      
}

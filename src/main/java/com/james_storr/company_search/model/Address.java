package com.james_storr.company_search.model;

import lombok.Data;

@Data
public class Address {
    private String locality;
    private String postal_code;
    private String premises;
    private String address_line_1;
    private String country;
}

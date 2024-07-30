package com.james_storr.company_search.model.truNarrative;

import com.james_storr.company_search.model.Address;

import lombok.Data;

@Data
public class SearchItem {
    private String company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    private Address address;   
}

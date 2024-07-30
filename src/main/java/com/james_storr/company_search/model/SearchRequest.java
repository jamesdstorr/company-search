package com.james_storr.company_search.model;

import lombok.Data;

@Data
public class SearchRequest {
    private String companyName;
    private String companyNumber;
}

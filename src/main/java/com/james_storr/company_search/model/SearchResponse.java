package com.james_storr.company_search.model;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchResponse {

    private String total_results;
    private List<Company> items;
    
}

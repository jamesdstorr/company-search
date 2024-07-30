package com.james_storr.company_search.model.truNarrative;

import java.util.List;

import lombok.Data;

@Data
public class TruProxyAPISearchResponse {   
    private String total_results;
    private List<SearchItem> items;
}

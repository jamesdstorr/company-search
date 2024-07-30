package com.james_storr.company_search.model.truNarrative;

import java.util.List;

import lombok.Data;

@Data
public class TruProxyResponse {
    private String page_Number;
    private String kind;
    private String total_results;
    private List<Item> items;
}

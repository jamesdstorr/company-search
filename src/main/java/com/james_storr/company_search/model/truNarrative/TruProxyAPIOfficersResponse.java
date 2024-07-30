package com.james_storr.company_search.model.truNarrative;

import java.util.List;

import com.james_storr.company_search.model.Officer;

import lombok.Data;

@Data
public class TruProxyAPIOfficersResponse {
    private List<Officer> items;
}

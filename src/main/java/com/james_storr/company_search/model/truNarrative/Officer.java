package com.james_storr.company_search.model.truNarrative;

import lombok.Data;

@Data
public class Officer {
    private String name;
    private String officer_role;
    private String appointed_on;
    private Address address;
}

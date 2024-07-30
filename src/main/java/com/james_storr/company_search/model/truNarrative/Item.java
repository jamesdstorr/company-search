package com.james_storr.company_search.model.truNarrative;

import java.util.List;

import lombok.Data;

@Data
public class Item {
    private String company_number;
    private String company_type;
    private String title;
    private String company_status;
    private String date_of_creation;
    private Address address;
    private List<Officer> officers;

}

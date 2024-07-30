package com.james_storr.company_search.hook.context;

import com.james_storr.company_search.model.truNarrative.TruProxyResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanySearchContext {
    private String companyName;
    private String companyNumber;
    private TruProxyResponse truProxyData;
    private TruProxyResponse databaseData;
    
}

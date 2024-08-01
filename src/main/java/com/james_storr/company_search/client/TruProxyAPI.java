package com.james_storr.company_search.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.james_storr.company_search.client.config.FeignConfig;
import com.james_storr.company_search.model.SearchResponse;
import com.james_storr.company_search.model.truNarrative.TruProxyAPIOfficersResponse;
@FeignClient(name = "tru-proxy", url = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1", configuration = FeignConfig.class)
public interface TruProxyAPI {

      
    @GetMapping("/Search")
    public SearchResponse search(
            @RequestHeader("x-api-key") String token,
            @RequestParam("Query") String query);
   
    @GetMapping("/Officers")
    public TruProxyAPIOfficersResponse officers(
        @RequestHeader("x-api-key") String token,
        @RequestParam("CompanyNumber") String companyNumber);    
}

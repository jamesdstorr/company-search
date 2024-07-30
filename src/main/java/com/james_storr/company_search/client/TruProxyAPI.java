package com.james_storr.company_search.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.james_storr.company_search.model.truNarrative.TruProxyResponse;

@FeignClient(name = "tru-proxy", url="https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1")
public interface TruProxyAPI {
    @GetMapping("/Search")
    public TruProxyResponse search(@RequestParam("Query") String query);
}

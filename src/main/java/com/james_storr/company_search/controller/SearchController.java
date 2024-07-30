package com.james_storr.company_search.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.james_storr.company_search.model.SearchRequest;
import com.james_storr.company_search.service.SearchServiceImpl;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1/search")
public class SearchController {

    private final SearchServiceImpl searchService;

    public SearchController(SearchServiceImpl searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/company")
    public CompletableFuture<ResponseEntity<?>> getCompanyByNameOrID(SearchRequest request) {        
        return searchService.getCompanyByNameOrID(request.getCompanyname(), request.getCompanyNumber()).thenApply(ResponseEntity::ok);
    }

}

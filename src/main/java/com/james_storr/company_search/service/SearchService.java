package com.james_storr.company_search.service;

import java.util.concurrent.CompletableFuture;

import com.james_storr.company_search.model.SearchResponse;

public interface SearchService {
     CompletableFuture<SearchResponse> getCompanyByNameOrID(String query, String company_id);
}

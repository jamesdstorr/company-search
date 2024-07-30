package com.james_storr.company_search.service;

import java.util.concurrent.CompletableFuture;

import com.james_storr.company_search.model.truNarrative.TruProxyResponse;

public interface SearchService {
     CompletableFuture<TruProxyResponse> getCompanyByNameOrID(String query, String company_id);
}

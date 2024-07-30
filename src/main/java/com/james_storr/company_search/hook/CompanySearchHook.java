package com.james_storr.company_search.hook;

import java.util.concurrent.CompletableFuture;

public interface CompanySearchHook<CONTEXT> {    
       CompletableFuture<CONTEXT> beforeProcessing(CONTEXT context);
       CompletableFuture<CONTEXT> afterProcessing(CONTEXT context);    
}

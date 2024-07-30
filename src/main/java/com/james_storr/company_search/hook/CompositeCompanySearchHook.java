package com.james_storr.company_search.hook;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CompositeCompanySearchHook<CONTEXT> implements CompanySearchHook<CONTEXT> {

    private final List<CompanySearchHook<CONTEXT>> hooks;

    public CompositeCompanySearchHook(List<CompanySearchHook<CONTEXT>> hooks) {
        this.hooks = hooks;
    }

    @Override
    public CompletableFuture<CONTEXT> beforeProcessing(CONTEXT context) {
        CompletableFuture<CONTEXT> future = CompletableFuture.completedFuture(context);
        for(CompanySearchHook<CONTEXT> hook : hooks){
            future = future.thenCompose(hook::beforeProcessing);
        }
        return future;
    }

    @Override
    public CompletableFuture<CONTEXT> afterProcessing(CONTEXT context) {
        CompletableFuture<CONTEXT> future = CompletableFuture.completedFuture(context);
        for(CompanySearchHook<CONTEXT> hook : hooks){
            future = future.thenCompose(hook::afterProcessing);
        }
        return future;
    }

}

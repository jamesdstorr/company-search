package com.james_storr.company_search.hook;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.james_storr.company_search.hook.annotations.FetchCompanyDataHooks;
import com.james_storr.company_search.hook.context.CompanySearchContext;

@FetchCompanyDataHooks
@Component
public class FetchCompanyDataFromDB implements CompanySearchHook<CompanySearchContext> {

    @Override
    public CompletableFuture<CompanySearchContext> beforeProcessing(CompanySearchContext companySearchContext) {
   
        return CompletableFuture.supplyAsync(() -> {
            //TODO: Call Database to see if Record exists for CompanyID
            return companySearchContext;
        });
        
    }

    @Override
    public CompletableFuture<CompanySearchContext> afterProcessing(CompanySearchContext context) {
        return CompletableFuture.completedFuture(context);
    }

    
}

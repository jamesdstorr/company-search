package com.james_storr.company_search.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.james_storr.company_search.exception.InvalidRequestException;
import com.james_storr.company_search.hook.CompanySearchHook;
import com.james_storr.company_search.hook.CompanySearchHookEnum;
import com.james_storr.company_search.hook.context.CompanySearchContext;
import com.james_storr.company_search.model.SearchResponse;




@Service
public class SearchServiceImpl implements SearchService {

  
    private final CompanySearchHook<CompanySearchContext> fetchCompanyDataHook;

    public SearchServiceImpl(Map<CompanySearchHookEnum, CompanySearchHook<CompanySearchContext>> companySearchHookMap){
 
        this.fetchCompanyDataHook = companySearchHookMap.get(CompanySearchHookEnum.FETCH_COMPANY_DATA);
    }

    @Override
    public CompletableFuture<SearchResponse> getCompanyByNameOrID(String query, String company_id) {        

        validateRequest(query, company_id);

        CompanySearchContext companySearchContext = CompanySearchContext.builder().companyNumber(company_id).companyName(query).build();
        
        return fetchCompanyDataHook.beforeProcessing(companySearchContext)        
        .thenCompose(fetchCompanyDataHook::afterProcessing)
        .thenApply(this::getResponse);   
    }

    private SearchResponse getResponse(CompanySearchContext ctx){        
            return ctx.getSearchResponse();       
    }

    private void validateRequest(String query, String companyId) {
        if (query == null && companyId == null) {
            throw new InvalidRequestException("Invalid request: Please provide request body.... \n {\n" + //
                                "    \"companyName\" : \"BBC Limited\",\n" + //
                                "    \"companyNumber\": \"06500244\"\n" + //                                
                                " }");
        }
    }
}

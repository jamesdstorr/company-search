package com.james_storr.company_search.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.james_storr.company_search.client.TruProxyAPI;
import com.james_storr.company_search.hook.CompanySearchHook;
import com.james_storr.company_search.hook.CompanySearchHookEnum;
import com.james_storr.company_search.hook.context.CompanySearchContext;
import com.james_storr.company_search.model.truNarrative.TruProxyResponse;



@Service
public class SearchServiceImpl implements SearchService {

    private final TruProxyAPI truProxy;
    private final CompanySearchHook<CompanySearchContext> fetchCompanyDataHook;

    @Value("${TruProxy.API.key}")
    private String apiKey;

    public SearchServiceImpl(TruProxyAPI truProxy, 
            Map<CompanySearchHookEnum, CompanySearchHook<CompanySearchContext>> companySearchHookMap){
        this.truProxy = truProxy;
        this.fetchCompanyDataHook = companySearchHookMap.get(CompanySearchHookEnum.FETCH_COMPANY_DATA);
    }

    

    @Override
    public CompletableFuture<TruProxyResponse> getCompanyByNameOrID(String query, String company_id) {        

        CompanySearchContext companySearchContext = CompanySearchContext.builder().companyName(company_id).companyName(query).build();
        
        //Hook will pull from Database if record exists for CompanyID
        return fetchCompanyDataHook.beforeProcessing(companySearchContext)
        .thenCompose(ctx -> {
            try {
                //check if context has data from database, if it doesn't then call truProxyAPI
                if(ctx.getDatabaseData() == null){
                    ctx.setTruProxyData(truProxy.search(query, apiKey));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return CompletableFuture.completedFuture(ctx);
        })
        .thenCompose(fetchCompanyDataHook::afterProcessing)
        .thenApply(this::getResponse);   
    }

    private TruProxyResponse getResponse(CompanySearchContext ctx){
        if(ctx.getDatabaseData()!= null){
            return ctx.getDatabaseData();
        }
        else {
            return ctx.getTruProxyData();
        }
    }
}

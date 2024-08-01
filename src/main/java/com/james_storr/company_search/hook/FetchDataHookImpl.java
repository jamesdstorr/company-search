package com.james_storr.company_search.hook;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.james_storr.company_search.client.TruProxyAPI;
import com.james_storr.company_search.exception.GenericFeignException;
import com.james_storr.company_search.exception.NotFoundException;
import com.james_storr.company_search.hook.annotations.FetchCompanyDataHooks;
import com.james_storr.company_search.hook.context.CompanySearchContext;
import com.james_storr.company_search.model.SearchResponse;
import com.james_storr.company_search.model.truNarrative.TruProxyAPIOfficersResponse;
import com.james_storr.company_search.repository.CompanyRepository;

import feign.FeignException;

@FetchCompanyDataHooks
@Component
public class FetchDataHookImpl implements CompanySearchHook<CompanySearchContext> {

    private TruProxyAPI truProxyAPI;
    private CompanyRepository companyRepository;

    @Value("${truproxy.api.key}")
    private String apiKey;

    public FetchDataHookImpl(TruProxyAPI truProxyAPI, CompanyRepository companyRepository) {
        this.truProxyAPI = truProxyAPI;
        this.companyRepository = companyRepository;
    }

    @Override
    public CompletableFuture<CompanySearchContext> beforeProcessing(CompanySearchContext ctx) {

        return CompletableFuture.supplyAsync(() -> {

            if (ctx.getCompanyNumber() != null) {
                ctx.setSearchResponse(
                        SearchResponse.builder().items(companyRepository.findByCompanyNumber(ctx.getCompanyNumber()))
                                .total_results("1").build());
            }

            if (ctx.getSearchResponse() == null || ctx.getSearchResponse().getItems().isEmpty()) {
                try {
                    if (ctx.getCompanyNumber() != null) {
                        ctx.setSearchResponse(truProxyAPI.search(apiKey, ctx.getCompanyNumber()));
                    } else {
                        ctx.setSearchResponse(truProxyAPI.search(apiKey, ctx.getCompanyName()));
                    }
                } catch (NotFoundException e) {
                    
                        throw new NotFoundException("No Company can be found");
                    
                }
                catch (GenericFeignException e) {
                    // Handle other Feign exceptions
                    throw new RuntimeException("Feign client error: " + e.getMessage(), e);
                }
            }
            return ctx;
        });
    }

    @Override
    public CompletableFuture<CompanySearchContext> afterProcessing(CompanySearchContext ctx) {
        return CompletableFuture.supplyAsync(() -> {
            ctx.getSearchResponse().getItems().stream()
                    .forEach(item -> {
                        if (item.getOfficers() == null || item.getOfficers().isEmpty()) {
                            TruProxyAPIOfficersResponse officersResponse = truProxyAPI.officers(apiKey,
                                    item.getCompanyNumber());
                            if (officersResponse.getItems() != null) {
                                item.setOfficers(officersResponse.getItems().stream()
                                        .filter(officer -> officer.getResigned_on() == null)
                                        .collect(Collectors.toList()));
                            }
                        }
                        SaveToDatabase(ctx);
                    });

            return ctx;
        });

    }

    private void SaveToDatabase(CompanySearchContext ctx) {
        companyRepository.saveAll(ctx.getSearchResponse().getItems());
    }

}

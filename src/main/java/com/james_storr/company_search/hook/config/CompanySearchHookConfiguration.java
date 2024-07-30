package com.james_storr.company_search.hook.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.james_storr.company_search.hook.CompanySearchHook;
import com.james_storr.company_search.hook.CompanySearchHookEnum;
import com.james_storr.company_search.hook.annotations.FetchCompanyDataHooks;
import com.james_storr.company_search.hook.context.CompanySearchContext;

@Configuration
public class CompanySearchHookConfiguration {

    @Bean
    public Map<CompanySearchHookEnum, CompanySearchHook<CompanySearchContext>> companySearchHooks(
            @FetchCompanyDataHooks CompanySearchHook<CompanySearchContext> fetchCompanyDataHook) {
        return Map.of(
                CompanySearchHookEnum.FETCH_COMPANY_DATA, fetchCompanyDataHook);
    }
}

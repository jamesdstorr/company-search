package com.james_storr.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.james_storr.company_search.CompanySearchApplication;
import com.james_storr.company_search.client.TruProxyAPI;
import com.james_storr.company_search.hook.CompanySearchHook;
import com.james_storr.company_search.hook.CompanySearchHookEnum;
import com.james_storr.company_search.hook.FetchDataHookImpl;
import com.james_storr.company_search.hook.context.CompanySearchContext;
import com.james_storr.company_search.model.Address;
import com.james_storr.company_search.model.Company;
import com.james_storr.company_search.model.Officer;
import com.james_storr.company_search.model.SearchResponse;
import com.james_storr.company_search.repository.CompanyRepository;
import com.james_storr.company_search.service.SearchServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CompanySearchApplication.class)
public class SearchServiceTest {

    @MockBean
    private TruProxyAPI truProxyAPI;

    @MockBean
    private FetchDataHookImpl fetchDataHook;

    @MockBean
    private CompanyRepository companyRepository;

    private SearchServiceImpl searchService;

    @BeforeEach
    void setUp() {
        Map<CompanySearchHookEnum, CompanySearchHook<CompanySearchContext>> companySearchHookMap = new HashMap<>();
        companySearchHookMap.put(CompanySearchHookEnum.FETCH_COMPANY_DATA, fetchDataHook);

        searchService = new SearchServiceImpl(companySearchHookMap);
    }

    @Test
    public void searchServiceReturnsCompany() throws InterruptedException, ExecutionException {
        // GIVEN
        String companyName = "BBC Limited";
        String companyNumber = "06500244";

        Address address = Address.builder()
                .address_line_1("123 Fake Street")
                .postal_code("FAKE123")
                .country("UK")
                .locality("FAKELOCAL")
                .premises("FAKEPREM")
                .build();

        Officer officer = Officer.builder().address(address)
                .name("John Doe")
                .appointed_on("2019-03-05")
                .build();

        List<Officer> officers = new ArrayList<>();
        officers.add(officer);

        Company company = Company.builder()
                .companyNumber("06500244")
                .title("BBC Limited")
                .companyType("ltd")
                .address(address)
                .officers(officers)
                .build();

        List<Company> companies = new ArrayList<>();
        companies.add(company);

        SearchResponse searchResponse = SearchResponse.builder()
                .total_results("1")
                .items(companies)
                .build();

        // Mocking repository and API behavior
        Mockito.when(companyRepository.findByCompanyNumber(companyNumber)).thenReturn(companies);
        Mockito.when(fetchDataHook.beforeProcessing(Mockito.any(CompanySearchContext.class)))
                .thenAnswer(invocation -> {
                    CompanySearchContext context = invocation.getArgument(0);
                    context.setSearchResponse(searchResponse);
                    return CompletableFuture.completedFuture(context);
                });
        Mockito.when(fetchDataHook.afterProcessing(Mockito.any(CompanySearchContext.class)))
                .thenAnswer(invocation -> CompletableFuture.completedFuture(invocation.getArgument(0)));

        // EXECUTE
        CompletableFuture<SearchResponse> testResponse = searchService.getCompanyByNameOrID(companyName, companyNumber);

        // THEN
        assertThat(testResponse.get().getTotal_results()).isEqualTo("1");
        assertThat(testResponse.get().getItems().get(0).getTitle()).isEqualTo("BBC Limited");
    }
}

package com.james_storr.api.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.james_storr.company_search.CompanySearchApplication;
import com.james_storr.company_search.client.TruProxyAPI;
import com.james_storr.company_search.model.Address;
import com.james_storr.company_search.model.Company;
import com.james_storr.company_search.model.Officer;
import com.james_storr.company_search.model.SearchResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CompanySearchApplication.class)
public class TruProxyTest {

    @MockBean
    private TruProxyAPI truProxyAPI;
   
    @BeforeEach
    public void setup() {
        // Any additional setup can go here if needed
    }

    @Test
    public void testTruProxyAPISearch() {
        // GIVEN
        String companyName = "BBC Limited";
        String testAPIKey = "TestKey";
        Address address = Address.builder()
                .address_line_1("123 Fake Street")
                .postal_code("FAKE123")
                .country("UK")
                .locality("FAKELOCAL")
                .premises("FAKEPREM")
                .build();

        Officer officer = Officer.builder().address(address).name("John Doe").appointed_on("2019-03-05").build();
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

        // WHEN
        Mockito.when(truProxyAPI.search(testAPIKey, companyName)).thenReturn(searchResponse);

        // EXECUTE
        SearchResponse response = truProxyAPI.search(testAPIKey, companyName);

        // THEN
        Mockito.verify(truProxyAPI).search(testAPIKey, companyName);
        assertThat(response.getTotal_results()).isEqualTo("1");
        assertThat(response.getItems().get(0).getTitle()).isEqualTo("BBC Limited");
    }
}

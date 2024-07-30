package com.james_storr.company_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompanySearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanySearchApplication.class, args);
	}

}

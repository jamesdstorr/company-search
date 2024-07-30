package com.james_storr.company_search.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.james_storr.company_search.model.Company;


public interface CompanyRepository extends MongoRepository<Company, String> {
    
    List<Company> findByCompanyNumber(String companyNumber);
}

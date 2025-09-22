package com.attio.crm.controller;

import com.attio.crm.entity.Company;
import com.attio.crm.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyRepository.findAllByOrderByNameAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyRepository.save(company);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            company.setName(companyDetails.getName());
            company.setIndustry(companyDetails.getIndustry());
            company.setSize(companyDetails.getSize());
            company.setWebsite(companyDetails.getWebsite());
            company.setStatus(companyDetails.getStatus());
            company.setRevenue(companyDetails.getRevenue());
            
            return ResponseEntity.ok(companyRepository.save(company));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        return companyRepository.findById(id)
                .map(company -> {
                    companyRepository.delete(company);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/industry/{industry}")
    public List<Company> getCompaniesByIndustry(@PathVariable String industry) {
        return companyRepository.findByIndustry(industry);
    }

    @GetMapping("/status/{status}")
    public List<Company> getCompaniesByStatus(@PathVariable String status) {
        return companyRepository.findByStatus(status);
    }
}
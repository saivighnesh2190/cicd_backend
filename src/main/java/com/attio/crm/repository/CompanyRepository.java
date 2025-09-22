package com.attio.crm.repository;

import com.attio.crm.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByOrderByNameAsc();
    List<Company> findByIndustry(String industry);
    List<Company> findByStatus(String status);
}
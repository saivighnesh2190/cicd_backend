package com.attio.crm.repository;

import com.attio.crm.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByCompanyId(Long companyId);
    List<Contact> findByOwnerId(Long ownerId);
    List<Contact> findByStatus(String status);
    List<Contact> findAllByOrderByFirstNameAsc();
}
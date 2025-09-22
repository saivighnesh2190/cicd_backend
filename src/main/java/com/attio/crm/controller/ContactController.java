package com.attio.crm.controller;

import com.attio.crm.entity.Contact;
import com.attio.crm.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactRepository.findAllByOrderByFirstNameAsc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactRepository.save(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contactDetails) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        
        if (optionalContact.isPresent()) {
            Contact contact = optionalContact.get();
            contact.setFirstName(contactDetails.getFirstName());
            contact.setLastName(contactDetails.getLastName());
            contact.setEmail(contactDetails.getEmail());
            contact.setPhone(contactDetails.getPhone());
            contact.setPosition(contactDetails.getPosition());
            contact.setStatus(contactDetails.getStatus());
            
            return ResponseEntity.ok(contactRepository.save(contact));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    public List<Contact> getContactsByCompany(@PathVariable Long companyId) {
        return contactRepository.findByCompanyId(companyId);
    }

    @GetMapping("/status/{status}")
    public List<Contact> getContactsByStatus(@PathVariable String status) {
        return contactRepository.findByStatus(status);
    }
}
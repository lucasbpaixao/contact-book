package com.majority.contactbookapi.repository;

import com.majority.contactbookapi.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    public List<Contact> findAllByOrderByNameAsc();
}

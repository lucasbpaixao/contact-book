package com.majority.contactbookapi.repository;

import com.majority.contactbookapi.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}

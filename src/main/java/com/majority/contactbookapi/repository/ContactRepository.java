package com.majority.contactbookapi.repository;

import com.majority.contactbookapi.model.Contact;
import com.majority.contactbookapi.model.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    public List<Contact> findAllByOrderByNameAsc();
    public List<Contact> findAllByUserAccountOrderByNameAsc(UserAccount userAccount);
}

package com.majority.contactbookapi.controller;

import com.majority.contactbookapi.model.Contact;
import com.majority.contactbookapi.repository.ContactRepository;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contact_book")
public class ContactBookController {


    @Autowired
    private ContactRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<Contact> newContact(@RequestBody @Valid Contact contact, UriComponentsBuilder uriBuilder){
        repository.save(contact);

        URI uri = uriBuilder.path("/contact_book/{id}").buildAndExpand(contact.getId()).toUri();

        return ResponseEntity.created(uri).body(contact);
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts(){
        List<Contact> contacts = repository.findAll();

        return ResponseEntity.ok(contacts);
    }
}

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
import javax.xml.ws.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;

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

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> removeContact(@PathVariable Long id){

        Optional<Contact> optional = repository.findById(id);

        if(optional.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Contact> alterContact(@PathVariable Long id, @RequestBody @Valid Contact contact){

        Optional<Contact> optional = repository.findById(id);

        if(optional.isPresent()){
            Contact dbContact = optional.get();

            dbContact.setName(contact.getName());
            dbContact.setEmail(contact.getEmail());
            dbContact.setPhoneNumber(contact.getPhoneNumber());

            return ResponseEntity.ok(dbContact);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id){
        Optional<Contact> optional = repository.findById(id);

        if(optional.isPresent()){
            Contact contact = optional.get();

            return ResponseEntity.ok(contact);
        }
        return ResponseEntity.notFound().build();
    }

}

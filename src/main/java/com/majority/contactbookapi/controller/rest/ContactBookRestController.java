package com.majority.contactbookapi.controller.rest;

import com.majority.contactbookapi.dto.ContactDto;
import com.majority.contactbookapi.model.Contact;
import com.majority.contactbookapi.model.UserAccount;
import com.majority.contactbookapi.repository.ContactRepository;
import com.majority.contactbookapi.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contact_book")
public class ContactBookRestController {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Contact> newContact(@RequestBody @Valid Contact contact, UriComponentsBuilder uriBuilder){
        contact.setUserAccount(getLoggedUser());
        
        repository.save(contact);

        URI uri = uriBuilder.path("/contact_book/{id}").buildAndExpand(contact.getId()).toUri();

        return ResponseEntity.created(uri).body(contact);
    }

    @GetMapping
    public ResponseEntity<List<ContactDto>> getAllContacts(){
        UserAccount userAccount = getLoggedUser();

        List<Contact> contacts = repository.findAllByUserAccountOrderByNameAsc(userAccount);

        List<ContactDto> returnList = ContactDto.convertList(contacts);
        return ResponseEntity.ok(returnList);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> removeContact(@PathVariable Long id){

        Optional<Contact> optional = repository.findById(id);

        if(optional.isPresent()){
            Contact contact = optional.get();
            if(contact.getUserAccount().getId() == getLoggedUser().getId()){
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ContactDto> alterContact(@PathVariable Long id, @RequestBody @Valid Contact contact){

        Optional<Contact> optional = repository.findById(id);

        if(optional.isPresent()){
            Contact dbContact = optional.get();

            if(dbContact.getUserAccount().getId() == getLoggedUser().getId()){
                dbContact.setName(contact.getName());
                dbContact.setEmail(contact.getEmail());
                dbContact.setPhoneNumber(contact.getPhoneNumber());

                return ResponseEntity.ok(new ContactDto(dbContact));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Long id){
        Optional<Contact> optional = repository.findById(id);

        if(optional.isPresent()){
            Contact contact = optional.get();

            if(contact.getUserAccount().getId() == getLoggedUser().getId()){
                return ResponseEntity.ok(new ContactDto(contact));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
        }
        return ResponseEntity.notFound().build();
    }

    private UserAccount getLoggedUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login;    

        if (principal instanceof UserDetails) {
            login = ((UserDetails)principal).getUsername();
        } else {
            login = principal.toString();
        }

        return userRepository.findByLogin(login).get();
    }

}

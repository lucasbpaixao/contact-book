package com.majority.contactbookapi.controller.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.majority.contactbookapi.model.UserAccount;
import com.majority.contactbookapi.repository.UserRepository;

@RestController
@RequestMapping("/create_account")
public class CreateAccountRestController {
    
    @Autowired
    UserRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<UserAccount> newContact(@RequestBody UserAccount userAccount, UriComponentsBuilder uriBuilder){
        
        userAccount.setPassword(BCrypt.hashpw(userAccount.getPassword(), BCrypt.gensalt()));
        repository.save(userAccount);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(userAccount.getId()).toUri();

        return ResponseEntity.created(uri).body(userAccount);
    }
}

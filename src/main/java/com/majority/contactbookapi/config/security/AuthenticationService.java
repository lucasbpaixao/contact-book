package com.majority.contactbookapi.config.security;

import com.majority.contactbookapi.model.User;
import com.majority.contactbookapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> optional = repository.findByLogin(login);

        if(optional.isPresent()){
            return optional.get();
        }

        throw new UsernameNotFoundException("Dados Invalidos");
    }
}

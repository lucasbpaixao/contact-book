package com.majority.contactbookapi.repository;

import com.majority.contactbookapi.model.Contact;
import com.majority.contactbookapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByLogin(String login);
}

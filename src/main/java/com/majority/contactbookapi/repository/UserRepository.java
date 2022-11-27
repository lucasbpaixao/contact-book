package com.majority.contactbookapi.repository;

import com.majority.contactbookapi.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    public Optional<UserAccount> findByLogin(String login);
}

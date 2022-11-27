package com.majority.contactbookapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.majority.contactbookapi.model.Contact;

public class ContactDto {
    
    public ContactDto(Contact contact) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.phoneNumber = contact.getPhoneNumber();
        this.email = contact.getEmail();
    }

    public ContactDto() {
    }

    private Long id;
    private String name;
    private String phoneNumber;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static List<ContactDto> convertList(List<Contact> contacts){
        List<ContactDto> contactDtos = new ArrayList<>();
        for (Contact contact : contacts) {
            contactDtos.add(new ContactDto(contact));
        }
        return contactDtos;
    }
}

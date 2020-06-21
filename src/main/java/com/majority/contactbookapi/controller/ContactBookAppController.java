package com.majority.contactbookapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class ContactBookAppController {

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/contact")
    public String contact() {

        return "contact.html";
    }
}

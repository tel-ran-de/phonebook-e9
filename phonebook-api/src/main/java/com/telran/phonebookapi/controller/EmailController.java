package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.service.EmailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class EmailController {
    final private EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/contact/{id}/email")
    public void create(@PathVariable("id") int contactId, @RequestBody EmailDto emailDto){
        emailDto.contactId = contactId;
        emailService.create(emailDto);
    }

    @GetMapping("/contact/{id}/email")
    public List<EmailDto> getByContact(@PathVariable("id") int contactId){
        return emailService.getAllByContact(contactId);
    }

    @PutMapping("/email")
    public void edit(@RequestBody EmailDto emailDto){
        emailService.edit(emailDto);
    }

    @DeleteMapping("/email/{id}")
    public void remove(@PathVariable("id") int emailId){
        emailService.removeById(emailId);
    }
}

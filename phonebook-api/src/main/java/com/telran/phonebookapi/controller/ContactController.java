package com.telran.phonebookapi.controller;

import com.telran.phonebookapi.dto.ContactDto;
import com.telran.phonebookapi.service.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ContactController {

    ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contact")
    public void create(@RequestBody @Valid ContactDto contactDto){
        contactService.addContact(contactDto);
    }

    @GetMapping("/contact/{id}")
    public ContactDto getById (@PathVariable int id){
        return contactService.getById(id);
    }

    @GetMapping("/contactFull/{id}")
    public ContactDto getByIdFullDetails (@PathVariable int id){
        return contactService.getByIdFullDetails(id);
    }

    @PutMapping("/contact")
    public void edit (@RequestBody @Valid ContactDto contactDto){
        contactService.edit(contactDto);
    }

    @DeleteMapping("contact/{id}")
    public void removeById(@PathVariable int id){
        contactService.removeById(id);
    }
}

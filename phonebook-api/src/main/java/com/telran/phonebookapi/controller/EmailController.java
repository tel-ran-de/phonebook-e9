package com.telran.phonebookapi.controller;
import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.service.EmailService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
public class EmailController {

    EmailService emailService;
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public void create(@RequestBody @Valid EmailDto emailDto){
        emailService.addEmail(emailDto);
    }

    @GetMapping("/email/{id}")
    public EmailDto getById (@PathVariable int id){
        return emailService.getById(id);
    }

    @PutMapping("/email")
    public void edit (@RequestBody @Valid EmailDto emailDto){
        emailService.edit(emailDto);
    }

    @DeleteMapping("/email/{id}")
    public void removeById(@PathVariable int id){
        emailService.removeById(id);
    }
}

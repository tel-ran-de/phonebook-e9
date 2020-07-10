package com.telran.phonebookapi.controller;
import com.telran.phonebookapi.dto.EmailDto;
import com.telran.phonebookapi.service.EmailService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/email")
public class EmailController {

    EmailService emailService;
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("")
    public void create(@RequestBody @Valid EmailDto emailDto){
        emailService.addEmail(emailDto);
    }

    @GetMapping("/{id}")
    public EmailDto getById (@PathVariable int id){
        return emailService.getById(id);
    }

    @PutMapping("")
    public void edit (@RequestBody @Valid EmailDto emailDto){
        emailService.edit(emailDto);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id){
        emailService.removeById(id);
    }
}

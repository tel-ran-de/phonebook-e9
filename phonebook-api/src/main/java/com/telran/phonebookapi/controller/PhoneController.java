package com.telran.phonebookapi.controller;
import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.service.PhoneService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
public class PhoneController {

    PhoneService phoneService;
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("/phone")
    public void create(@RequestBody @Valid PhoneDto phoneDto){
        phoneService.addPhone(phoneDto);
    }

    @GetMapping("/phone/{id}")
    public PhoneDto getById (@PathVariable int id){
        return phoneService.getById(id);
    }

    @PutMapping("/phone")
    public void edit (@RequestBody @Valid PhoneDto phoneDto){
        phoneService.edit(phoneDto);
    }

    @DeleteMapping("/phone/{id}")
    public void removeById(@PathVariable int id){
        phoneService.removeById(id);
    }

}

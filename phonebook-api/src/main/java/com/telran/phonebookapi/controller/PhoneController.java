package com.telran.phonebookapi.controller;
import com.telran.phonebookapi.dto.PhoneDto;
import com.telran.phonebookapi.service.PhoneService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/phone")
public class PhoneController {

    PhoneService phoneService;
    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("")
    public void create(@RequestBody @Valid PhoneDto phoneDto){
        phoneService.addPhone(phoneDto);
    }

    @GetMapping("/{id}")
    public PhoneDto getById (@PathVariable int id){
        return phoneService.getById(id);
    }

    @PutMapping("")
    public void edit (@RequestBody @Valid PhoneDto phoneDto){
        phoneService.edit(phoneDto);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id){
        phoneService.removeById(id);
    }
}

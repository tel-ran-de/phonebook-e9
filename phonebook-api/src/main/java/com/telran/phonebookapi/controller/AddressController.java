package com.telran.phonebookapi.controller;
import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.service.AddressService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/address")
public class AddressController {

    AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("")
    public void create(@RequestBody @Valid AddressDto addressDto){
        addressService.addAddress(addressDto);
    }

    @GetMapping("/{id}")
    public AddressDto getById (@PathVariable int id){
        return addressService.getById(id);
    }

    @PutMapping("")
    public void edit (@RequestBody @Valid AddressDto addressDto){
        addressService.edit(addressDto);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id){
        addressService.removeById(id);
    }
}

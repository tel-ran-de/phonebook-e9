package com.telran.phonebookapi.controller;
import com.telran.phonebookapi.dto.AddressDto;
import com.telran.phonebookapi.service.AddressService;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@CrossOrigin
public class AddressController {

    AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/address")
    public void create(@RequestBody @Valid AddressDto addressDto){
        addressService.addAddress(addressDto);
    }

    @GetMapping("/address/{id}")
    public AddressDto getById (@PathVariable int id){
        return addressService.getById(id);
    }

    @PutMapping("/address")
    public void edit (@RequestBody @Valid AddressDto addressDto){
        addressService.edit(addressDto);
    }

    @DeleteMapping("address/{id}")
    public void removeById(@PathVariable int id){
        addressService.removeById(id);
    }

}

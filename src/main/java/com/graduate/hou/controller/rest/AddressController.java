package com.graduate.hou.controller.rest;

import com.graduate.hou.dto.request.AddressDTO;
import com.graduate.hou.entity.Address;
import com.graduate.hou.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping
    Address createAddress(@RequestBody AddressDTO addressDTO){
        return addressService.createAddress(addressDTO);
    }

    @GetMapping
    List<Address> getAllAddress(){
        return addressService.getAllAddress();
    }

    @PutMapping("/{id}")
    Address updateAddress (@PathVariable Long id, @RequestBody AddressDTO addressDTO){
        return addressService.updateAddress(id, addressDTO);
    }

    @DeleteMapping("/{id}")
    String deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return "Xóa thành công";
    }
}

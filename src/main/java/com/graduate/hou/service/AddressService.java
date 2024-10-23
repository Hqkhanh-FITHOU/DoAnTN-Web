package com.graduate.hou.service;

import com.graduate.hou.dto.request.AddressDTO;
import com.graduate.hou.entity.Address;


import java.util.List;

public interface AddressService {
    List<Address> getAllAddress();
    Address createAddress(AddressDTO addressDTO);
    Address updateAddress(Long id, AddressDTO addressDTO);
    void deleteAddress(Long id);
}

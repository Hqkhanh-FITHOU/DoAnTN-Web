package com.graduate.hou.service.impl;

import com.graduate.hou.dto.request.AddressDTO;
import com.graduate.hou.entity.Address;
import com.graduate.hou.repository.AddressRepository;
import com.graduate.hou.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;


    @Override
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        Address address = new Address();

        address.setDetail(addressDTO.getDetail());

        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        Address address = optionalAddress.get();

        address.setDetail(addressDTO.getDetail());

        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}

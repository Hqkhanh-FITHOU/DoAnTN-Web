package com.graduate.hou.service.impl;

import com.graduate.hou.dto.AddressDTO;
import com.graduate.hou.entity.Address;
import com.graduate.hou.entity.User;
import com.graduate.hou.repository.AddressRepository;
import com.graduate.hou.repository.UsersRepository;
import com.graduate.hou.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        Address address = new Address();

        User user = usersRepository.findById(addressDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("chưa đăng nhập"));
        address.setUser(user);

        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setDistrict(addressDTO.getDistrict());
        address.setWard(addressDTO.getWard());
        address.setAddressType(addressDTO.getAddressType());

        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        Address address = optionalAddress.get();

        User user = usersRepository.findById(addressDTO.getUserId())
                .orElseThrow(()-> new RuntimeException("chưa đăng nhập"));
        address.setUser(user);

        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setDistrict(addressDTO.getDistrict());
        address.setWard(addressDTO.getWard());
        address.setAddressType(addressDTO.getAddressType());

        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }
}

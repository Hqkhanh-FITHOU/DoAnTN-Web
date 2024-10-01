package com.graduate.hou.repository;

import com.graduate.hou.entity.Address;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface AddressRepository extends JpaRepository<Address, Long> {
}

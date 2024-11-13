package com.graduate.hou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduate.hou.enums.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;


    @Column(nullable = false)
    private String detail; 


    @Column(nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private AddressType addressType; //loại địa chỉ
}

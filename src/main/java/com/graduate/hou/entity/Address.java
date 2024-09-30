package com.graduate.hou.entity;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String number; //số nhà 

    @Column(nullable = false, length = 100)
    private String street; //đường

    @Column(nullable = false, length = 100)
    private String city; //thành phố/tỉnh

    @Column(nullable = false, length = 100)
    private String district; // quận

    @Column(nullable = false, length = 100)
    private String ward; //phường

    @Column(nullable = false, length = 100)
    private AddressType addressType; //phường
}

package com.graduate.hou.dto;

import com.graduate.hou.entity.User;
import com.graduate.hou.enums.AddressType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long addressId;

    private Long user;

    private String number; //số nhà

    private String street; //đường

    private String city; //thành phố/tỉnh

    private String district; // quận

    private String ward; //phường

    private AddressType addressType; //phường
}

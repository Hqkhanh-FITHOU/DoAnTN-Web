package com.graduate.hou.dto;


import com.graduate.hou.enums.AddressType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long addressId;

    private Long userId;

    private String number; //số nhà

    private String street; //đường

    private String city; //thành phố/tỉnh

    private String district; // quận

    private String ward; //phường

    private AddressType addressType; //phường
}

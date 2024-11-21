package com.graduate.hou.dto.request;


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

    private String detail; //số nhà

    private AddressType addressType; //phường
}

package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.ecommerce.dtosimple.AddressSimpleDto;

import com.example.modules.ecommerce.dtosimple.OrderSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String phone;

    private List<AddressSimpleDto> addresses;

    private List<OrderSimpleDto> orders;

}
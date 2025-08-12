package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import com.example.modules.ecommerce.dtosimple.UserSimpleDto;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Long id;

    private String street;

    private String city;

    private String postalCode;

    private String country;

    private UserSimpleDto user;

}
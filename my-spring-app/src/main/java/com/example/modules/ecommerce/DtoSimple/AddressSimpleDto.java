package com.example.modules.ecommerce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressSimpleDto {
private Long id;

    private String street;

    private String city;

    private String postalCode;

    private String country;

}
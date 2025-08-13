package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.dto.AddressDto;
import com.example.modules.ecommerce.dtosimple.AddressSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto toDto(Address address);

    AddressSimpleDto toSimpleDto(Address address);

    @InheritInverseConfiguration
    Address toEntity(AddressDto addressDto);

    List<AddressDto> toDtoList(List<Address> addressList);

    List<Address> toEntityList(List<AddressDto> addressDtoList);

}
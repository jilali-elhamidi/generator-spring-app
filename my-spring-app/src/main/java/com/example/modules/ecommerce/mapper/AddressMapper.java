package com.example.modules.ecommerce.mapper;

import com.example.modules.ecommerce.model.Address;
import com.example.modules.ecommerce.dto.AddressDto;
import com.example.modules.ecommerce.dtosimple.AddressSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<Address, AddressDto, AddressSimpleDto> {
}
package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Country;
import com.example.modules.entertainment_ecosystem.dto.CountryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.CountrySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface CountryMapper extends BaseMapper<Country, CountryDto, CountrySimpleDto> {
}
package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Country;
import com.example.modules.entertainment_ecosystem.dto.CountryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.CountrySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryMapper INSTANCE = Mappers.getMapper(CountryMapper.class);

    CountryDto toDto(Country country);

    CountrySimpleDto toSimpleDto(Country country);

    @InheritInverseConfiguration
    Country toEntity(CountryDto countryDto);

    List<CountryDto> toDtoList(List<Country> countryList);

    List<Country> toEntityList(List<CountryDto> countryDtoList);

    void updateEntityFromDto(CountryDto dto, @MappingTarget Country entity);

}
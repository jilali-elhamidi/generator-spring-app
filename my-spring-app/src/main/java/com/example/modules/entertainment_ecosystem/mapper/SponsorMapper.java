package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Sponsor;
import com.example.modules.entertainment_ecosystem.dto.SponsorDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SponsorSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface SponsorMapper {

    SponsorMapper INSTANCE = Mappers.getMapper(SponsorMapper.class);

    SponsorDto toDto(Sponsor sponsor);

    SponsorSimpleDto toSimpleDto(Sponsor sponsor);

    @InheritInverseConfiguration
    Sponsor toEntity(SponsorDto sponsorDto);

    List<SponsorDto> toDtoList(List<Sponsor> sponsorList);

    List<Sponsor> toEntityList(List<SponsorDto> sponsorDtoList);

}
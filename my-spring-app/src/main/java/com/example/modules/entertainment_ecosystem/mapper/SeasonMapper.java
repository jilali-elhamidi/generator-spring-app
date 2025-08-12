package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Season;
import com.example.modules.entertainment_ecosystem.dto.SeasonDto;
import com.example.modules.entertainment_ecosystem.dtosimple.SeasonSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface SeasonMapper {

    SeasonMapper INSTANCE = Mappers.getMapper(SeasonMapper.class);

    SeasonDto toDto(Season season);

    SeasonSimpleDto toSimpleDto(Season season);

    @InheritInverseConfiguration
    Season toEntity(SeasonDto seasonDto);

    List<SeasonDto> toDtoList(List<Season> seasonList);

    List<Season> toEntityList(List<SeasonDto> seasonDtoList);

}
package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.DevelopmentStudio;
import com.example.modules.entertainment_ecosystem.dto.DevelopmentStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.DevelopmentStudioSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface DevelopmentStudioMapper {

    DevelopmentStudioMapper INSTANCE = Mappers.getMapper(DevelopmentStudioMapper.class);

    DevelopmentStudioDto toDto(DevelopmentStudio developmentstudio);

    DevelopmentStudioSimpleDto toSimpleDto(DevelopmentStudio developmentstudio);

    @InheritInverseConfiguration
    DevelopmentStudio toEntity(DevelopmentStudioDto developmentstudioDto);

    List<DevelopmentStudioDto> toDtoList(List<DevelopmentStudio> developmentstudioList);

    List<DevelopmentStudio> toEntityList(List<DevelopmentStudioDto> developmentstudioDtoList);

}
package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicFormat;
import com.example.modules.entertainment_ecosystem.dto.MusicFormatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicFormatSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MusicFormatMapper {

    MusicFormatMapper INSTANCE = Mappers.getMapper(MusicFormatMapper.class);

    MusicFormatDto toDto(MusicFormat musicformat);

    MusicFormatSimpleDto toSimpleDto(MusicFormat musicformat);

    @InheritInverseConfiguration
    MusicFormat toEntity(MusicFormatDto musicformatDto);

    List<MusicFormatDto> toDtoList(List<MusicFormat> musicformatList);

    List<MusicFormat> toEntityList(List<MusicFormatDto> musicformatDtoList);

    void updateEntityFromDto(MusicFormatDto dto, @MappingTarget MusicFormat entity);

}
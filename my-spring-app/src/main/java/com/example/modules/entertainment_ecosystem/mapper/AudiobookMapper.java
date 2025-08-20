package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Audiobook;
import com.example.modules.entertainment_ecosystem.dto.AudiobookDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AudiobookSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AudiobookMapper {

    AudiobookMapper INSTANCE = Mappers.getMapper(AudiobookMapper.class);

    AudiobookDto toDto(Audiobook audiobook);

    AudiobookSimpleDto toSimpleDto(Audiobook audiobook);

    @InheritInverseConfiguration
    Audiobook toEntity(AudiobookDto audiobookDto);

    List<AudiobookDto> toDtoList(List<Audiobook> audiobookList);

    List<Audiobook> toEntityList(List<AudiobookDto> audiobookDtoList);

    void updateEntityFromDto(AudiobookDto dto, @MappingTarget Audiobook entity);

}
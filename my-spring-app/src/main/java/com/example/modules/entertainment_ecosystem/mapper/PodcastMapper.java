package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Podcast;
import com.example.modules.entertainment_ecosystem.dto.PodcastDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PodcastMapper {

    PodcastMapper INSTANCE = Mappers.getMapper(PodcastMapper.class);

    PodcastDto toDto(Podcast podcast);

    PodcastSimpleDto toSimpleDto(Podcast podcast);

    @InheritInverseConfiguration
    Podcast toEntity(PodcastDto podcastDto);

    List<PodcastDto> toDtoList(List<Podcast> podcastList);

    List<Podcast> toEntityList(List<PodcastDto> podcastDtoList);

    void updateEntityFromDto(PodcastDto dto, @MappingTarget Podcast entity);

}
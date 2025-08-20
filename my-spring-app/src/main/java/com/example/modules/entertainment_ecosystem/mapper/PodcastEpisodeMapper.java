package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PodcastEpisode;
import com.example.modules.entertainment_ecosystem.dto.PodcastEpisodeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastEpisodeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PodcastEpisodeMapper {

    PodcastEpisodeMapper INSTANCE = Mappers.getMapper(PodcastEpisodeMapper.class);

    PodcastEpisodeDto toDto(PodcastEpisode podcastepisode);

    PodcastEpisodeSimpleDto toSimpleDto(PodcastEpisode podcastepisode);

    @InheritInverseConfiguration
    PodcastEpisode toEntity(PodcastEpisodeDto podcastepisodeDto);

    List<PodcastEpisodeDto> toDtoList(List<PodcastEpisode> podcastepisodeList);

    List<PodcastEpisode> toEntityList(List<PodcastEpisodeDto> podcastepisodeDtoList);

    void updateEntityFromDto(PodcastEpisodeDto dto, @MappingTarget PodcastEpisode entity);

}
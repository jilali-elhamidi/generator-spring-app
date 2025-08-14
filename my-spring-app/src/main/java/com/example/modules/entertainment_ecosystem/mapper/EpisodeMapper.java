package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Episode;
import com.example.modules.entertainment_ecosystem.dto.EpisodeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EpisodeMapper {

    EpisodeMapper INSTANCE = Mappers.getMapper(EpisodeMapper.class);

    EpisodeDto toDto(Episode episode);

    EpisodeSimpleDto toSimpleDto(Episode episode);

    @InheritInverseConfiguration
    Episode toEntity(EpisodeDto episodeDto);

    List<EpisodeDto> toDtoList(List<Episode> episodeList);

    List<Episode> toEntityList(List<EpisodeDto> episodeDtoList);

    void updateEntityFromDto(EpisodeDto dto, @MappingTarget Episode entity);

}
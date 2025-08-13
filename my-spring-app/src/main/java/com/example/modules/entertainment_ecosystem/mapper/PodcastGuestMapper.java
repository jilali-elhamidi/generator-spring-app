package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.PodcastGuest;
import com.example.modules.entertainment_ecosystem.dto.PodcastGuestDto;
import com.example.modules.entertainment_ecosystem.dtosimple.PodcastGuestSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PodcastGuestMapper {

    PodcastGuestMapper INSTANCE = Mappers.getMapper(PodcastGuestMapper.class);

    PodcastGuestDto toDto(PodcastGuest podcastguest);

    PodcastGuestSimpleDto toSimpleDto(PodcastGuest podcastguest);

    @InheritInverseConfiguration
    PodcastGuest toEntity(PodcastGuestDto podcastguestDto);

    List<PodcastGuestDto> toDtoList(List<PodcastGuest> podcastguestList);

    List<PodcastGuest> toEntityList(List<PodcastGuestDto> podcastguestDtoList);

}
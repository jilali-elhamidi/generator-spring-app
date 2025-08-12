package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.dto.ArtistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ArtistSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ArtistMapper {

    ArtistMapper INSTANCE = Mappers.getMapper(ArtistMapper.class);

    ArtistDto toDto(Artist artist);

    ArtistSimpleDto toSimpleDto(Artist artist);

    @InheritInverseConfiguration
    Artist toEntity(ArtistDto artistDto);

    List<ArtistDto> toDtoList(List<Artist> artistList);

    List<Artist> toEntityList(List<ArtistDto> artistDtoList);

}
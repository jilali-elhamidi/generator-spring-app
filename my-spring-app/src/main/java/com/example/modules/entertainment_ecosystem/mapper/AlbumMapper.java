package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.dto.AlbumDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AlbumSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

    AlbumDto toDto(Album album);

    AlbumSimpleDto toSimpleDto(Album album);

    @InheritInverseConfiguration
    Album toEntity(AlbumDto albumDto);

    List<AlbumDto> toDtoList(List<Album> albumList);

    List<Album> toEntityList(List<AlbumDto> albumDtoList);

    void updateEntityFromDto(AlbumDto dto, @MappingTarget Album entity);

}
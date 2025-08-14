package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserPlaylistItem;
import com.example.modules.entertainment_ecosystem.dto.UserPlaylistItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistItemSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserPlaylistItemMapper {

    UserPlaylistItemMapper INSTANCE = Mappers.getMapper(UserPlaylistItemMapper.class);

    UserPlaylistItemDto toDto(UserPlaylistItem userplaylistitem);

    UserPlaylistItemSimpleDto toSimpleDto(UserPlaylistItem userplaylistitem);

    @InheritInverseConfiguration
    UserPlaylistItem toEntity(UserPlaylistItemDto userplaylistitemDto);

    List<UserPlaylistItemDto> toDtoList(List<UserPlaylistItem> userplaylistitemList);

    List<UserPlaylistItem> toEntityList(List<UserPlaylistItemDto> userplaylistitemDtoList);

    void updateEntityFromDto(UserPlaylistItemDto dto, @MappingTarget UserPlaylistItem entity);

}
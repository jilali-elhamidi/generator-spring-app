package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.UserPlaylist;
import com.example.modules.entertainment_ecosystem.dto.UserPlaylistDto;
import com.example.modules.entertainment_ecosystem.dtosimple.UserPlaylistSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface UserPlaylistMapper {

    UserPlaylistMapper INSTANCE = Mappers.getMapper(UserPlaylistMapper.class);

    UserPlaylistDto toDto(UserPlaylist userplaylist);

    UserPlaylistSimpleDto toSimpleDto(UserPlaylist userplaylist);

    @InheritInverseConfiguration
    UserPlaylist toEntity(UserPlaylistDto userplaylistDto);

    List<UserPlaylistDto> toDtoList(List<UserPlaylist> userplaylistList);

    List<UserPlaylist> toEntityList(List<UserPlaylistDto> userplaylistDtoList);

}
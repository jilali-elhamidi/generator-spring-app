package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Album;
import com.example.modules.entertainment_ecosystem.dto.AlbumDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AlbumSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AlbumMapper extends BaseMapper<Album, AlbumDto, AlbumSimpleDto> {
}
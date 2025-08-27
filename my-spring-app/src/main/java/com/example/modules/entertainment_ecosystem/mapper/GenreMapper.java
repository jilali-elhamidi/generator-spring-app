package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.dto.GenreDto;
import com.example.modules.entertainment_ecosystem.dtosimple.GenreSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface GenreMapper extends BaseMapper<Genre, GenreDto, GenreSimpleDto> {
}
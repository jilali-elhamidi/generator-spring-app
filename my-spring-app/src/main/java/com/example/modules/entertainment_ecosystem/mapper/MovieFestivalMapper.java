package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieFestival;
import com.example.modules.entertainment_ecosystem.dto.MovieFestivalDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieFestivalSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MovieFestivalMapper extends BaseMapper<MovieFestival, MovieFestivalDto, MovieFestivalSimpleDto> {
}
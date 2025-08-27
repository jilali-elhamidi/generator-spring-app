package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.dto.MovieDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MovieMapper extends BaseMapper<Movie, MovieDto, MovieSimpleDto> {
}
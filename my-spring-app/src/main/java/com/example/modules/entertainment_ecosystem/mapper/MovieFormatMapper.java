package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieFormat;
import com.example.modules.entertainment_ecosystem.dto.MovieFormatDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieFormatSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MovieFormatMapper extends BaseMapper<MovieFormat, MovieFormatDto, MovieFormatSimpleDto> {
}
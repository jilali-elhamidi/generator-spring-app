package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieStudio;
import com.example.modules.entertainment_ecosystem.dto.MovieStudioDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieStudioSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MovieStudioMapper extends BaseMapper<MovieStudio, MovieStudioDto, MovieStudioSimpleDto> {
}
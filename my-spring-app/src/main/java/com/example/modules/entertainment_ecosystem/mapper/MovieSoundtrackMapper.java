package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieSoundtrack;
import com.example.modules.entertainment_ecosystem.dto.MovieSoundtrackDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieSoundtrackSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MovieSoundtrackMapper extends BaseMapper<MovieSoundtrack, MovieSoundtrackDto, MovieSoundtrackSimpleDto> {
}
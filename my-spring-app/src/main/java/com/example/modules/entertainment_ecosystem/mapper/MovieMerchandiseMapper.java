package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MovieMerchandise;
import com.example.modules.entertainment_ecosystem.dto.MovieMerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MovieMerchandiseSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MovieMerchandiseMapper extends BaseMapper<MovieMerchandise, MovieMerchandiseDto, MovieMerchandiseSimpleDto> {
}
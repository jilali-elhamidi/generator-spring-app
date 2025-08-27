package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MusicGenreCategory;
import com.example.modules.entertainment_ecosystem.dto.MusicGenreCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MusicGenreCategorySimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MusicGenreCategoryMapper extends BaseMapper<MusicGenreCategory, MusicGenreCategoryDto, MusicGenreCategorySimpleDto> {
}
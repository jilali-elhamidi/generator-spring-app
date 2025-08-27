package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.AudiobookChapter;
import com.example.modules.entertainment_ecosystem.dto.AudiobookChapterDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AudiobookChapterSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AudiobookChapterMapper extends BaseMapper<AudiobookChapter, AudiobookChapterDto, AudiobookChapterSimpleDto> {
}
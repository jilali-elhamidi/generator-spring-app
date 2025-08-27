package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Audiobook;
import com.example.modules.entertainment_ecosystem.dto.AudiobookDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AudiobookSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AudiobookMapper extends BaseMapper<Audiobook, AudiobookDto, AudiobookSimpleDto> {
}
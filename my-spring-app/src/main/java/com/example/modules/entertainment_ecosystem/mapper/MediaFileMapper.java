package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MediaFile;
import com.example.modules.entertainment_ecosystem.dto.MediaFileDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MediaFileSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MediaFileMapper extends BaseMapper<MediaFile, MediaFileDto, MediaFileSimpleDto> {
}
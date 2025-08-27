package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.dto.MediaFileDto;
import com.example.modules.social_media.dtosimple.MediaFileSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface MediaFileMapper extends BaseMapper<MediaFile, MediaFileDto, MediaFileSimpleDto> {
}
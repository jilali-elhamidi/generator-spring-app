package com.example.modules.social_media.mapper;

import com.example.modules.social_media.model.MediaFile;
import com.example.modules.social_media.dto.MediaFileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MediaFileMapper {

MediaFileMapper INSTANCE = Mappers.getMapper(MediaFileMapper.class);


    

MediaFileDto toDto(MediaFile mediafile);

MediaFile toEntity(MediaFileDto mediafileDto);

List<MediaFileDto> toDtoList(List<MediaFile> mediafileList);

    List<MediaFile> toEntityList(List<MediaFileDto> mediafileDtoList);
        }
package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MediaFile;
import com.example.modules.entertainment_ecosystem.dto.MediaFileDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MediaFileSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MediaFileMapper {

    MediaFileMapper INSTANCE = Mappers.getMapper(MediaFileMapper.class);

    MediaFileDto toDto(MediaFile mediafile);

    MediaFileSimpleDto toSimpleDto(MediaFile mediafile);

    @InheritInverseConfiguration
    MediaFile toEntity(MediaFileDto mediafileDto);

    List<MediaFileDto> toDtoList(List<MediaFile> mediafileList);

    List<MediaFile> toEntityList(List<MediaFileDto> mediafileDtoList);

}
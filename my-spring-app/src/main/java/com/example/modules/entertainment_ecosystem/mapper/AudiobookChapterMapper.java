package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.AudiobookChapter;
import com.example.modules.entertainment_ecosystem.dto.AudiobookChapterDto;
import com.example.modules.entertainment_ecosystem.dtosimple.AudiobookChapterSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface AudiobookChapterMapper {

    AudiobookChapterMapper INSTANCE = Mappers.getMapper(AudiobookChapterMapper.class);

    AudiobookChapterDto toDto(AudiobookChapter audiobookchapter);

    AudiobookChapterSimpleDto toSimpleDto(AudiobookChapter audiobookchapter);

    @InheritInverseConfiguration
    AudiobookChapter toEntity(AudiobookChapterDto audiobookchapterDto);

    List<AudiobookChapterDto> toDtoList(List<AudiobookChapter> audiobookchapterList);

    List<AudiobookChapter> toEntityList(List<AudiobookChapterDto> audiobookchapterDtoList);

    void updateEntityFromDto(AudiobookChapterDto dto, @MappingTarget AudiobookChapter entity);

}
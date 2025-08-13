package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentLanguage;
import com.example.modules.entertainment_ecosystem.dto.ContentLanguageDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentLanguageSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ContentLanguageMapper {

    ContentLanguageMapper INSTANCE = Mappers.getMapper(ContentLanguageMapper.class);

    ContentLanguageDto toDto(ContentLanguage contentlanguage);

    ContentLanguageSimpleDto toSimpleDto(ContentLanguage contentlanguage);

    @InheritInverseConfiguration
    ContentLanguage toEntity(ContentLanguageDto contentlanguageDto);

    List<ContentLanguageDto> toDtoList(List<ContentLanguage> contentlanguageList);

    List<ContentLanguage> toEntityList(List<ContentLanguageDto> contentlanguageDtoList);

}
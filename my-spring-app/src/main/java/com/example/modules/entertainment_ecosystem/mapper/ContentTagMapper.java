package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentTag;
import com.example.modules.entertainment_ecosystem.dto.ContentTagDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentTagSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ContentTagMapper {

    ContentTagMapper INSTANCE = Mappers.getMapper(ContentTagMapper.class);

    ContentTagDto toDto(ContentTag contenttag);

    ContentTagSimpleDto toSimpleDto(ContentTag contenttag);

    @InheritInverseConfiguration
    ContentTag toEntity(ContentTagDto contenttagDto);

    List<ContentTagDto> toDtoList(List<ContentTag> contenttagList);

    List<ContentTag> toEntityList(List<ContentTagDto> contenttagDtoList);

}
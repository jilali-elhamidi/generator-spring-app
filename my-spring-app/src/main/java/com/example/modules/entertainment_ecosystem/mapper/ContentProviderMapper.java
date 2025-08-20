package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentProvider;
import com.example.modules.entertainment_ecosystem.dto.ContentProviderDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentProviderSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ContentProviderMapper {

    ContentProviderMapper INSTANCE = Mappers.getMapper(ContentProviderMapper.class);

    ContentProviderDto toDto(ContentProvider contentprovider);

    ContentProviderSimpleDto toSimpleDto(ContentProvider contentprovider);

    @InheritInverseConfiguration
    ContentProvider toEntity(ContentProviderDto contentproviderDto);

    List<ContentProviderDto> toDtoList(List<ContentProvider> contentproviderList);

    List<ContentProvider> toEntityList(List<ContentProviderDto> contentproviderDtoList);

    void updateEntityFromDto(ContentProviderDto dto, @MappingTarget ContentProvider entity);

}
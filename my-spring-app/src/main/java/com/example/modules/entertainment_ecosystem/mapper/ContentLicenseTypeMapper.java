package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ContentLicenseType;
import com.example.modules.entertainment_ecosystem.dto.ContentLicenseTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ContentLicenseTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ContentLicenseTypeMapper {

    ContentLicenseTypeMapper INSTANCE = Mappers.getMapper(ContentLicenseTypeMapper.class);

    ContentLicenseTypeDto toDto(ContentLicenseType contentlicensetype);

    ContentLicenseTypeSimpleDto toSimpleDto(ContentLicenseType contentlicensetype);

    @InheritInverseConfiguration
    ContentLicenseType toEntity(ContentLicenseTypeDto contentlicensetypeDto);

    List<ContentLicenseTypeDto> toDtoList(List<ContentLicenseType> contentlicensetypeList);

    List<ContentLicenseType> toEntityList(List<ContentLicenseTypeDto> contentlicensetypeDtoList);

    void updateEntityFromDto(ContentLicenseTypeDto dto, @MappingTarget ContentLicenseType entity);

}
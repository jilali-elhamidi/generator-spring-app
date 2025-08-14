package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.StreamingContentLicense;
import com.example.modules.entertainment_ecosystem.dto.StreamingContentLicenseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.StreamingContentLicenseSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface StreamingContentLicenseMapper {

    StreamingContentLicenseMapper INSTANCE = Mappers.getMapper(StreamingContentLicenseMapper.class);

    StreamingContentLicenseDto toDto(StreamingContentLicense streamingcontentlicense);

    StreamingContentLicenseSimpleDto toSimpleDto(StreamingContentLicense streamingcontentlicense);

    @InheritInverseConfiguration
    StreamingContentLicense toEntity(StreamingContentLicenseDto streamingcontentlicenseDto);

    List<StreamingContentLicenseDto> toDtoList(List<StreamingContentLicense> streamingcontentlicenseList);

    List<StreamingContentLicense> toEntityList(List<StreamingContentLicenseDto> streamingcontentlicenseDtoList);

    void updateEntityFromDto(StreamingContentLicenseDto dto, @MappingTarget StreamingContentLicense entity);

}
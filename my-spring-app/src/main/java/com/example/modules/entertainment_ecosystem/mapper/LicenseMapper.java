package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.License;
import com.example.modules.entertainment_ecosystem.dto.LicenseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.LicenseSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface LicenseMapper {

    LicenseMapper INSTANCE = Mappers.getMapper(LicenseMapper.class);

    LicenseDto toDto(License license);

    LicenseSimpleDto toSimpleDto(License license);

    @InheritInverseConfiguration
    License toEntity(LicenseDto licenseDto);

    List<LicenseDto> toDtoList(List<License> licenseList);

    List<License> toEntityList(List<LicenseDto> licenseDtoList);

}
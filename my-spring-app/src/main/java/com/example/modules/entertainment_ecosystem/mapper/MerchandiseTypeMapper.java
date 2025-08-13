package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseTypeDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseTypeSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseTypeMapper {

    MerchandiseTypeMapper INSTANCE = Mappers.getMapper(MerchandiseTypeMapper.class);

    MerchandiseTypeDto toDto(MerchandiseType merchandisetype);

    MerchandiseTypeSimpleDto toSimpleDto(MerchandiseType merchandisetype);

    @InheritInverseConfiguration
    MerchandiseType toEntity(MerchandiseTypeDto merchandisetypeDto);

    List<MerchandiseTypeDto> toDtoList(List<MerchandiseType> merchandisetypeList);

    List<MerchandiseType> toEntityList(List<MerchandiseTypeDto> merchandisetypeDtoList);

}
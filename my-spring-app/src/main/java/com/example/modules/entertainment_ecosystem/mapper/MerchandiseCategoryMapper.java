package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseCategoryDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseCategorySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseCategoryMapper {

    MerchandiseCategoryMapper INSTANCE = Mappers.getMapper(MerchandiseCategoryMapper.class);

    MerchandiseCategoryDto toDto(MerchandiseCategory merchandisecategory);

    MerchandiseCategorySimpleDto toSimpleDto(MerchandiseCategory merchandisecategory);

    @InheritInverseConfiguration
    MerchandiseCategory toEntity(MerchandiseCategoryDto merchandisecategoryDto);

    List<MerchandiseCategoryDto> toDtoList(List<MerchandiseCategory> merchandisecategoryList);

    List<MerchandiseCategory> toEntityList(List<MerchandiseCategoryDto> merchandisecategoryDtoList);

    void updateEntityFromDto(MerchandiseCategoryDto dto, @MappingTarget MerchandiseCategory entity);

}
package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseCollectionDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseCollectionSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseCollectionMapper {

    MerchandiseCollectionMapper INSTANCE = Mappers.getMapper(MerchandiseCollectionMapper.class);

    MerchandiseCollectionDto toDto(MerchandiseCollection merchandisecollection);

    MerchandiseCollectionSimpleDto toSimpleDto(MerchandiseCollection merchandisecollection);

    @InheritInverseConfiguration
    MerchandiseCollection toEntity(MerchandiseCollectionDto merchandisecollectionDto);

    List<MerchandiseCollectionDto> toDtoList(List<MerchandiseCollection> merchandisecollectionList);

    List<MerchandiseCollection> toEntityList(List<MerchandiseCollectionDto> merchandisecollectionDtoList);

    void updateEntityFromDto(MerchandiseCollectionDto dto, @MappingTarget MerchandiseCollection entity);

}
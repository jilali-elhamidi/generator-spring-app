package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseOrderItemDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderItemSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseOrderItemMapper {

    MerchandiseOrderItemMapper INSTANCE = Mappers.getMapper(MerchandiseOrderItemMapper.class);

    MerchandiseOrderItemDto toDto(MerchandiseOrderItem merchandiseorderitem);

    MerchandiseOrderItemSimpleDto toSimpleDto(MerchandiseOrderItem merchandiseorderitem);

    @InheritInverseConfiguration
    MerchandiseOrderItem toEntity(MerchandiseOrderItemDto merchandiseorderitemDto);

    List<MerchandiseOrderItemDto> toDtoList(List<MerchandiseOrderItem> merchandiseorderitemList);

    List<MerchandiseOrderItem> toEntityList(List<MerchandiseOrderItemDto> merchandiseorderitemDtoList);

    void updateEntityFromDto(MerchandiseOrderItemDto dto, @MappingTarget MerchandiseOrderItem entity);

}
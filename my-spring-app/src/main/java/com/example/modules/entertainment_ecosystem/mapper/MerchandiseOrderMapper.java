package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseOrderDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseOrderSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseOrderMapper {

    MerchandiseOrderMapper INSTANCE = Mappers.getMapper(MerchandiseOrderMapper.class);

    MerchandiseOrderDto toDto(MerchandiseOrder merchandiseorder);

    MerchandiseOrderSimpleDto toSimpleDto(MerchandiseOrder merchandiseorder);

    @InheritInverseConfiguration
    MerchandiseOrder toEntity(MerchandiseOrderDto merchandiseorderDto);

    List<MerchandiseOrderDto> toDtoList(List<MerchandiseOrder> merchandiseorderList);

    List<MerchandiseOrder> toEntityList(List<MerchandiseOrderDto> merchandiseorderDtoList);

}
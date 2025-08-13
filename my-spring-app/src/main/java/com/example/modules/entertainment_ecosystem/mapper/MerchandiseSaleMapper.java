package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseSaleDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSaleSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseSaleMapper {

    MerchandiseSaleMapper INSTANCE = Mappers.getMapper(MerchandiseSaleMapper.class);

    MerchandiseSaleDto toDto(MerchandiseSale merchandisesale);

    MerchandiseSaleSimpleDto toSimpleDto(MerchandiseSale merchandisesale);

    @InheritInverseConfiguration
    MerchandiseSale toEntity(MerchandiseSaleDto merchandisesaleDto);

    List<MerchandiseSaleDto> toDtoList(List<MerchandiseSale> merchandisesaleList);

    List<MerchandiseSale> toEntityList(List<MerchandiseSaleDto> merchandisesaleDtoList);

}
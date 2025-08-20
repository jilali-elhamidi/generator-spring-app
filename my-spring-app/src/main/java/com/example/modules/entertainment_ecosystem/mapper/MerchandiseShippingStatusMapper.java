package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.MerchandiseShippingStatus;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseShippingStatusDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseShippingStatusSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseShippingStatusMapper {

    MerchandiseShippingStatusMapper INSTANCE = Mappers.getMapper(MerchandiseShippingStatusMapper.class);

    MerchandiseShippingStatusDto toDto(MerchandiseShippingStatus merchandiseshippingstatus);

    MerchandiseShippingStatusSimpleDto toSimpleDto(MerchandiseShippingStatus merchandiseshippingstatus);

    @InheritInverseConfiguration
    MerchandiseShippingStatus toEntity(MerchandiseShippingStatusDto merchandiseshippingstatusDto);

    List<MerchandiseShippingStatusDto> toDtoList(List<MerchandiseShippingStatus> merchandiseshippingstatusList);

    List<MerchandiseShippingStatus> toEntityList(List<MerchandiseShippingStatusDto> merchandiseshippingstatusDtoList);

    void updateEntityFromDto(MerchandiseShippingStatusDto dto, @MappingTarget MerchandiseShippingStatus entity);

}
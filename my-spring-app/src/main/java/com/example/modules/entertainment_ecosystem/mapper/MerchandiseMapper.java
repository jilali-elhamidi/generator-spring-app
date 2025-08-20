package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.dto.MerchandiseDto;
import com.example.modules.entertainment_ecosystem.dtosimple.MerchandiseSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MerchandiseMapper {

    MerchandiseMapper INSTANCE = Mappers.getMapper(MerchandiseMapper.class);

    MerchandiseDto toDto(Merchandise merchandise);

    MerchandiseSimpleDto toSimpleDto(Merchandise merchandise);

    @InheritInverseConfiguration
    Merchandise toEntity(MerchandiseDto merchandiseDto);

    List<MerchandiseDto> toDtoList(List<Merchandise> merchandiseList);

    List<Merchandise> toEntityList(List<MerchandiseDto> merchandiseDtoList);

    void updateEntityFromDto(MerchandiseDto dto, @MappingTarget Merchandise entity);

}
package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Shift;
import com.example.modules.entertainment_ecosystem.dto.ShiftDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ShiftSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ShiftMapper {

    ShiftMapper INSTANCE = Mappers.getMapper(ShiftMapper.class);

    ShiftDto toDto(Shift shift);

    ShiftSimpleDto toSimpleDto(Shift shift);

    @InheritInverseConfiguration
    Shift toEntity(ShiftDto shiftDto);

    List<ShiftDto> toDtoList(List<Shift> shiftList);

    List<Shift> toEntityList(List<ShiftDto> shiftDtoList);

    void updateEntityFromDto(ShiftDto dto, @MappingTarget Shift entity);

}
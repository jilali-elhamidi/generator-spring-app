package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Reimbursement;
import com.example.modules.healthcare_management.dto.ReimbursementDto;
import com.example.modules.healthcare_management.dtosimple.ReimbursementSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ReimbursementMapper {

    ReimbursementMapper INSTANCE = Mappers.getMapper(ReimbursementMapper.class);

    ReimbursementDto toDto(Reimbursement reimbursement);

    ReimbursementSimpleDto toSimpleDto(Reimbursement reimbursement);

    @InheritInverseConfiguration
    Reimbursement toEntity(ReimbursementDto reimbursementDto);

    List<ReimbursementDto> toDtoList(List<Reimbursement> reimbursementList);

    List<Reimbursement> toEntityList(List<ReimbursementDto> reimbursementDtoList);
}
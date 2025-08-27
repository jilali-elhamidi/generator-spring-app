package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Reimbursement;
import com.example.modules.healthcare_management.dto.ReimbursementDto;
import com.example.modules.healthcare_management.dtosimple.ReimbursementSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ReimbursementMapper extends BaseMapper<Reimbursement, ReimbursementDto, ReimbursementSimpleDto> {
}
package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Invoice;
import com.example.modules.project_management.dto.InvoiceDto;
import com.example.modules.project_management.dtosimple.InvoiceSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper extends BaseMapper<Invoice, InvoiceDto, InvoiceSimpleDto> {
}
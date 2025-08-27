package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.Invoice;
import com.example.modules.entertainment_ecosystem.dto.InvoiceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.InvoiceSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper extends BaseMapper<Invoice, InvoiceDto, InvoiceSimpleDto> {
}
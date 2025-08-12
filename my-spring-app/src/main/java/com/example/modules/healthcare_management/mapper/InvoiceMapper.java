package com.example.modules.healthcare_management.mapper;

import com.example.modules.healthcare_management.model.Invoice;
import com.example.modules.healthcare_management.dto.InvoiceDto;
import com.example.modules.healthcare_management.dtosimple.InvoiceSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    InvoiceDto toDto(Invoice invoice);

    InvoiceSimpleDto toSimpleDto(Invoice invoice);

    @InheritInverseConfiguration
    Invoice toEntity(InvoiceDto invoiceDto);

    List<InvoiceDto> toDtoList(List<Invoice> invoiceList);

    List<Invoice> toEntityList(List<InvoiceDto> invoiceDtoList);

}
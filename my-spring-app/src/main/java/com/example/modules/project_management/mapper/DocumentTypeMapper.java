package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.DocumentType;
import com.example.modules.project_management.dto.DocumentTypeDto;
import com.example.modules.project_management.dtosimple.DocumentTypeSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface DocumentTypeMapper extends BaseMapper<DocumentType, DocumentTypeDto, DocumentTypeSimpleDto> {
}
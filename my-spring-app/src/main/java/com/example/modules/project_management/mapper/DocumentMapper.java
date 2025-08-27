package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Document;
import com.example.modules.project_management.dto.DocumentDto;
import com.example.modules.project_management.dtosimple.DocumentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends BaseMapper<Document, DocumentDto, DocumentSimpleDto> {
}
package com.example.modules.project_management.mapper;

import com.example.modules.project_management.model.Attachment;
import com.example.modules.project_management.dto.AttachmentDto;
import com.example.modules.project_management.dtosimple.AttachmentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends BaseMapper<Attachment, AttachmentDto, AttachmentSimpleDto> {
}
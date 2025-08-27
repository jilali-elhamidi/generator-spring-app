package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import com.example.modules.entertainment_ecosystem.dto.ReportedContentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReportedContentSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface ReportedContentMapper extends BaseMapper<ReportedContent, ReportedContentDto, ReportedContentSimpleDto> {
}
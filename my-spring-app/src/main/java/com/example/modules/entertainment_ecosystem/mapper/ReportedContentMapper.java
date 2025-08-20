package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import com.example.modules.entertainment_ecosystem.dto.ReportedContentDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReportedContentSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ReportedContentMapper {

    ReportedContentMapper INSTANCE = Mappers.getMapper(ReportedContentMapper.class);

    ReportedContentDto toDto(ReportedContent reportedcontent);

    ReportedContentSimpleDto toSimpleDto(ReportedContent reportedcontent);

    @InheritInverseConfiguration
    ReportedContent toEntity(ReportedContentDto reportedcontentDto);

    List<ReportedContentDto> toDtoList(List<ReportedContent> reportedcontentList);

    List<ReportedContent> toEntityList(List<ReportedContentDto> reportedcontentDtoList);

    void updateEntityFromDto(ReportedContentDto dto, @MappingTarget ReportedContent entity);

}
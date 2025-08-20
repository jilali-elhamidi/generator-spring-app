package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EventAudience;
import com.example.modules.entertainment_ecosystem.dto.EventAudienceDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EventAudienceSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EventAudienceMapper {

    EventAudienceMapper INSTANCE = Mappers.getMapper(EventAudienceMapper.class);

    EventAudienceDto toDto(EventAudience eventaudience);

    EventAudienceSimpleDto toSimpleDto(EventAudience eventaudience);

    @InheritInverseConfiguration
    EventAudience toEntity(EventAudienceDto eventaudienceDto);

    List<EventAudienceDto> toDtoList(List<EventAudience> eventaudienceList);

    List<EventAudience> toEntityList(List<EventAudienceDto> eventaudienceDtoList);

    void updateEntityFromDto(EventAudienceDto dto, @MappingTarget EventAudience entity);

}
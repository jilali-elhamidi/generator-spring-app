package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.ReviewReply;
import com.example.modules.entertainment_ecosystem.dto.ReviewReplyDto;
import com.example.modules.entertainment_ecosystem.dtosimple.ReviewReplySimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface ReviewReplyMapper {

    ReviewReplyMapper INSTANCE = Mappers.getMapper(ReviewReplyMapper.class);

    ReviewReplyDto toDto(ReviewReply reviewreply);

    ReviewReplySimpleDto toSimpleDto(ReviewReply reviewreply);

    @InheritInverseConfiguration
    ReviewReply toEntity(ReviewReplyDto reviewreplyDto);

    List<ReviewReplyDto> toDtoList(List<ReviewReply> reviewreplyList);

    List<ReviewReply> toEntityList(List<ReviewReplyDto> reviewreplyDtoList);

    void updateEntityFromDto(ReviewReplyDto dto, @MappingTarget ReviewReply entity);

}
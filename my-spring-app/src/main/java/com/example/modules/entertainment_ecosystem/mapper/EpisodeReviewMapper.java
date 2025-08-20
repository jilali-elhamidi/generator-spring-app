package com.example.modules.entertainment_ecosystem.mapper;

import com.example.modules.entertainment_ecosystem.model.EpisodeReview;
import com.example.modules.entertainment_ecosystem.dto.EpisodeReviewDto;
import com.example.modules.entertainment_ecosystem.dtosimple.EpisodeReviewSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingTarget;
import java.util.List;


@Mapper(componentModel = "spring")
public interface EpisodeReviewMapper {

    EpisodeReviewMapper INSTANCE = Mappers.getMapper(EpisodeReviewMapper.class);

    EpisodeReviewDto toDto(EpisodeReview episodereview);

    EpisodeReviewSimpleDto toSimpleDto(EpisodeReview episodereview);

    @InheritInverseConfiguration
    EpisodeReview toEntity(EpisodeReviewDto episodereviewDto);

    List<EpisodeReviewDto> toDtoList(List<EpisodeReview> episodereviewList);

    List<EpisodeReview> toEntityList(List<EpisodeReviewDto> episodereviewDtoList);

    void updateEntityFromDto(EpisodeReviewDto dto, @MappingTarget EpisodeReview entity);

}
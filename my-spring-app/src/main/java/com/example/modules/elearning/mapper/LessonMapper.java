package com.example.modules.elearning.mapper;

import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.dto.LessonDto;
import com.example.modules.elearning.dtosimple.LessonSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    LessonDto toDto(Lesson lesson);

    LessonSimpleDto toSimpleDto(Lesson lesson);

    @InheritInverseConfiguration
    Lesson toEntity(LessonDto lessonDto);

    List<LessonDto> toDtoList(List<Lesson> lessonList);

    List<Lesson> toEntityList(List<LessonDto> lessonDtoList);

}
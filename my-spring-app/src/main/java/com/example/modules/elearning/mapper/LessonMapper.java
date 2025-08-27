package com.example.modules.elearning.mapper;

import com.example.modules.elearning.model.Lesson;
import com.example.modules.elearning.dto.LessonDto;
import com.example.modules.elearning.dtosimple.LessonSimpleDto;
import com.example.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * 
 */
@Mapper(componentModel = "spring")
public interface LessonMapper extends BaseMapper<Lesson, LessonDto, LessonSimpleDto> {
}
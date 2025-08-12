package com.example.modules.elearning.mapper;

import com.example.modules.elearning.model.Instructor;
import com.example.modules.elearning.dto.InstructorDto;
import com.example.modules.elearning.dtosimple.InstructorSimpleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface InstructorMapper {

    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    InstructorDto toDto(Instructor instructor);

    InstructorSimpleDto toSimpleDto(Instructor instructor);

    @InheritInverseConfiguration
    Instructor toEntity(InstructorDto instructorDto);

    List<InstructorDto> toDtoList(List<Instructor> instructorList);

    List<Instructor> toEntityList(List<InstructorDto> instructorDtoList);

}
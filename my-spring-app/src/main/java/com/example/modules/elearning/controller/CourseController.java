package com.example.modules.elearning.controller;

import com.example.modules.elearning.dto.CourseDto;
import com.example.modules.elearning.model.Course;
import com.example.modules.elearning.mapper.CourseMapper;
import com.example.modules.elearning.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    public CourseController(CourseService courseService,
                                    CourseMapper courseMapper) {
        this.courseService = courseService;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<Course> entities = courseService.findAll();
        return ResponseEntity.ok(courseMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return courseService.findById(id)
                .map(courseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(
            @Valid @RequestBody CourseDto courseDto,
            UriComponentsBuilder uriBuilder) {

        Course entity = courseMapper.toEntity(courseDto);
        Course saved = courseService.save(entity);

        URI location = uriBuilder
                                .path("/api/courses/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(courseMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CourseDto>> createAllCourses(
            @Valid @RequestBody List<CourseDto> courseDtoList,
            UriComponentsBuilder uriBuilder) {

        List<Course> entities = courseMapper.toEntityList(courseDtoList);
        List<Course> savedEntities = courseService.saveAll(entities);

        URI location = uriBuilder.path("/api/courses").build().toUri();

        return ResponseEntity.created(location).body(courseMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDto courseDto) {


        Course entityToUpdate = courseMapper.toEntity(courseDto);
        Course updatedEntity = courseService.update(id, entityToUpdate);

        return ResponseEntity.ok(courseMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        boolean deleted = courseService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
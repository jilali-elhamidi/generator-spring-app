package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.ReportedContentDto;
import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import com.example.modules.entertainment_ecosystem.mapper.ReportedContentMapper;
import com.example.modules.entertainment_ecosystem.service.ReportedContentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reportedcontents")
public class ReportedContentController {

    private final ReportedContentService reportedcontentService;
    private final ReportedContentMapper reportedcontentMapper;

    public ReportedContentController(ReportedContentService reportedcontentService,
                                    ReportedContentMapper reportedcontentMapper) {
        this.reportedcontentService = reportedcontentService;
        this.reportedcontentMapper = reportedcontentMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReportedContentDto>> getAllReportedContents() {
        List<ReportedContent> entities = reportedcontentService.findAll();
        return ResponseEntity.ok(reportedcontentMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportedContentDto> getReportedContentById(@PathVariable Long id) {
        return reportedcontentService.findById(id)
                .map(reportedcontentMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReportedContentDto> createReportedContent(
            @Valid @RequestBody ReportedContentDto reportedcontentDto,
            UriComponentsBuilder uriBuilder) {

        ReportedContent entity = reportedcontentMapper.toEntity(reportedcontentDto);
        ReportedContent saved = reportedcontentService.save(entity);

        URI location = uriBuilder
                                .path("/api/reportedcontents/{id}")
                                .buildAndExpand(saved.getId())
                                .toUri();

        return ResponseEntity.created(location).body(reportedcontentMapper.toDto(saved));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ReportedContentDto>> createAllReportedContents(
            @Valid @RequestBody List<ReportedContentDto> reportedcontentDtoList,
            UriComponentsBuilder uriBuilder) {

        List<ReportedContent> entities = reportedcontentMapper.toEntityList(reportedcontentDtoList);
        List<ReportedContent> savedEntities = reportedcontentService.saveAll(entities);

        URI location = uriBuilder.path("/api/reportedcontents").build().toUri();

        return ResponseEntity.created(location).body(reportedcontentMapper.toDtoList(savedEntities));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportedContentDto> updateReportedContent(
            @PathVariable Long id,
            @Valid @RequestBody ReportedContentDto reportedcontentDto) {


        ReportedContent entityToUpdate = reportedcontentMapper.toEntity(reportedcontentDto);
        ReportedContent updatedEntity = reportedcontentService.update(id, entityToUpdate);

        return ResponseEntity.ok(reportedcontentMapper.toDto(updatedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportedContent(@PathVariable Long id) {
        boolean deleted = reportedcontentService.deleteById(id);

        if (!deleted) {
        return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
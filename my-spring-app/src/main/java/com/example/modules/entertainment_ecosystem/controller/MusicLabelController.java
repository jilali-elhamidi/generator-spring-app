package com.example.modules.entertainment_ecosystem.controller;

import com.example.modules.entertainment_ecosystem.dto.MusicLabelDto;
import com.example.modules.entertainment_ecosystem.model.MusicLabel;
import com.example.modules.entertainment_ecosystem.mapper.MusicLabelMapper;
import com.example.modules.entertainment_ecosystem.service.MusicLabelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/musiclabels")
public class MusicLabelController {

    private final MusicLabelService musiclabelService;
    private final MusicLabelMapper musiclabelMapper;

    public MusicLabelController(MusicLabelService musiclabelService,
                                    MusicLabelMapper musiclabelMapper) {
        this.musiclabelService = musiclabelService;
        this.musiclabelMapper = musiclabelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MusicLabelDto>> getAllMusicLabels() {
        List<MusicLabel> entities = musiclabelService.findAll();
        return ResponseEntity.ok(musiclabelMapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicLabelDto> getMusicLabelById(@PathVariable Long id) {
        return musiclabelService.findById(id)
                .map(musiclabelMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MusicLabelDto> createMusicLabel(
            @Valid @RequestBody MusicLabelDto musiclabelDto,
            UriComponentsBuilder uriBuilder) {

        MusicLabel entity = musiclabelMapper.toEntity(musiclabelDto);
        MusicLabel saved = musiclabelService.save(entity);
        URI location = uriBuilder.path("/api/musiclabels/{id}")
                                 .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(musiclabelMapper.toDto(saved));
    }

            @PutMapping("/{id}")
            public ResponseEntity<MusicLabelDto> updateMusicLabel(
                @PathVariable Long id,
                @RequestBody MusicLabelDto musiclabelDto) {

                // Transformer le DTO en entity pour le service
                MusicLabel entityToUpdate = musiclabelMapper.toEntity(musiclabelDto);

                // Appel du service update
                MusicLabel updatedEntity = musiclabelService.update(id, entityToUpdate);

                // Transformer l’entity mise à jour en DTO pour le retour
                MusicLabelDto updatedDto = musiclabelMapper.toDto(updatedEntity);

                return ResponseEntity.ok(updatedDto);
                }
                @DeleteMapping("/{id}")
                public ResponseEntity<Void> deleteMusicLabel(@PathVariable Long id) {
                    boolean deleted = musiclabelService.deleteById(id);

                    if (!deleted) {
                    // Renvoie 404 si l'ID n'existe pas
                    return ResponseEntity.notFound().build();
                    }

                    // Renvoie 204 si suppression réussie
                    return ResponseEntity.noContent().build();
                    }
}
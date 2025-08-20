package com.example.modules.social_media.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.social_media.model.MediaFile;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaFileRepository extends BaseRepository<MediaFile, Long> {

}

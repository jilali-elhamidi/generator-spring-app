package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportedContentRepository extends BaseRepository<ReportedContent, Long> {

}

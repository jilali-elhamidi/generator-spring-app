package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Audiobook;
import org.springframework.stereotype.Repository;

@Repository
public interface AudiobookRepository extends BaseRepository<Audiobook, Long> {

}

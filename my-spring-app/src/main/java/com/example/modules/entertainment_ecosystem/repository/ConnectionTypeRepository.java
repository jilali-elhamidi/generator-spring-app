package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.ConnectionType;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionTypeRepository extends BaseRepository<ConnectionType, Long> {
}

package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalAssetType;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalAssetTypeRepository extends BaseRepository<DigitalAssetType, Long> {
}

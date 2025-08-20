package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalAsset;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalAssetRepository extends BaseRepository<DigitalAsset, Long> {

}

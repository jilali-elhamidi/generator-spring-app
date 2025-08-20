package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.FeatureFlag;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureFlagRepository extends BaseRepository<FeatureFlag, Long> {

}

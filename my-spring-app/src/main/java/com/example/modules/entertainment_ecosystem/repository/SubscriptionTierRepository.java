package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.SubscriptionTier;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionTierRepository extends BaseRepository<SubscriptionTier, Long> {
}

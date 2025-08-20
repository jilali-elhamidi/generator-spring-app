package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalPurchaseRepository extends BaseRepository<DigitalPurchase, Long> {

}

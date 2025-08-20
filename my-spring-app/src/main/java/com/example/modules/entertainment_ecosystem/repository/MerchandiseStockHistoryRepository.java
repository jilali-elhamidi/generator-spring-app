package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandiseStockHistoryRepository extends BaseRepository<MerchandiseStockHistory, Long> {
}

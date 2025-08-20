package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandiseOrderRepository extends BaseRepository<MerchandiseOrder, Long> {
}

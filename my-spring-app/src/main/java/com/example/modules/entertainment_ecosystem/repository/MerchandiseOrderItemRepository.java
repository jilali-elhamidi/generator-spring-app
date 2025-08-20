package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandiseOrderItemRepository extends BaseRepository<MerchandiseOrderItem, Long> {

}

package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandiseInventoryRepository extends BaseRepository<MerchandiseInventory, Long> {

}

package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandiseCategoryRepository extends BaseRepository<MerchandiseCategory, Long> {

}

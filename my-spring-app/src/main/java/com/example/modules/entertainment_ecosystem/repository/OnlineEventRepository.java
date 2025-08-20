package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.OnlineEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineEventRepository extends BaseRepository<OnlineEvent, Long> {

}

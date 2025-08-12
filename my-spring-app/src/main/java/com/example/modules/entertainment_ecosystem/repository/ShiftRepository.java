package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Shift;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends BaseRepository<Shift, Long> {
}

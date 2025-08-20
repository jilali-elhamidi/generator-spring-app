package com.example.modules.healthcare_management.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.healthcare_management.model.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends BaseRepository<Room, Long> {

}

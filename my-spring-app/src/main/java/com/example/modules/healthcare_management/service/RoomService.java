package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Room;
import com.example.modules.healthcare_management.repository.RoomRepository;
import com.example.modules.healthcare_management.model.Appointment;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class RoomService extends BaseService<Room> {

    protected final RoomRepository roomRepository;

    public RoomService(RoomRepository repository)
    {
        super(repository);
        this.roomRepository = repository;
    }

    @Override
    public Room save(Room room) {

        if (room.getAppointments() != null) {
            for (Appointment item : room.getAppointments()) {
            item.setRoom(room);
            }
        }

        return roomRepository.save(room);
    }


    public Room update(Long id, Room roomRequest) {
        Room existing = roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found"));

    // Copier les champs simples
        existing.setRoomNumber(roomRequest.getRoomNumber());
        existing.setType(roomRequest.getType());
        existing.setCapacity(roomRequest.getCapacity());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getAppointments().clear();
        if (roomRequest.getAppointments() != null) {
            for (var item : roomRequest.getAppointments()) {
            item.setRoom(existing);
            existing.getAppointments().add(item);
            }
        }

        return roomRepository.save(existing);
    }
}
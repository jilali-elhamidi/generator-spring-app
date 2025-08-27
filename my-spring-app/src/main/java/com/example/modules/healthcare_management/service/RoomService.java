package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Room;
import com.example.modules.healthcare_management.repository.RoomRepository;

import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.repository.AppointmentRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class RoomService extends BaseService<Room> {

    protected final RoomRepository roomRepository;
    
    protected final AppointmentRepository appointmentsRepository;
    

    public RoomService(RoomRepository repository, AppointmentRepository appointmentsRepository)
    {
        super(repository);
        this.roomRepository = repository;
        
        this.appointmentsRepository = appointmentsRepository;
        
    }

    @Transactional
    @Override
    public Room save(Room room) {
    // ---------- OneToMany ----------
        if (room.getAppointments() != null) {
            List<Appointment> managedAppointments = new ArrayList<>();
            for (Appointment item : room.getAppointments()) {
                if (item.getId() != null) {
                    Appointment existingItem = appointmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Appointment not found"));

                     existingItem.setRoom(room);
                     managedAppointments.add(existingItem);
                } else {
                    item.setRoom(room);
                    managedAppointments.add(item);
                }
            }
            room.setAppointments(managedAppointments);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return roomRepository.save(room);
}

    @Transactional
    @Override
    public Room update(Long id, Room roomRequest) {
        Room existing = roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found"));

    // Copier les champs simples
        existing.setRoomNumber(roomRequest.getRoomNumber());
        existing.setType(roomRequest.getType());
        existing.setCapacity(roomRequest.getCapacity());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getAppointments().clear();

        if (roomRequest.getAppointments() != null) {
            for (var item : roomRequest.getAppointments()) {
                Appointment existingItem;
                if (item.getId() != null) {
                    existingItem = appointmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Appointment not found"));
                } else {
                existingItem = item;
                }

                existingItem.setRoom(existing);
                existing.getAppointments().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return roomRepository.save(existing);
}

    // Pagination simple
    public Page<Room> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Room> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Room.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Room> saveAll(List<Room> roomList) {
        return super.saveAll(roomList);
    }

}
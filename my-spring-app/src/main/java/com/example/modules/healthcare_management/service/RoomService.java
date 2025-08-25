package com.example.modules.healthcare_management.service;

import com.example.core.service.BaseService;
import com.example.modules.healthcare_management.model.Room;
import com.example.modules.healthcare_management.repository.RoomRepository;
import com.example.modules.healthcare_management.model.Appointment;
import com.example.modules.healthcare_management.repository.AppointmentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class RoomService extends BaseService<Room> {

    protected final RoomRepository roomRepository;
    private final AppointmentRepository appointmentsRepository;

    public RoomService(RoomRepository repository, AppointmentRepository appointmentsRepository)
    {
        super(repository);
        this.roomRepository = repository;
        this.appointmentsRepository = appointmentsRepository;
    }

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


    public Room update(Long id, Room roomRequest) {
        Room existing = roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found"));

    // Copier les champs simples
        existing.setRoomNumber(roomRequest.getRoomNumber());
        existing.setType(roomRequest.getType());
        existing.setCapacity(roomRequest.getCapacity());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Room> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Room entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getAppointments() != null) {
            for (var child : entity.getAppointments()) {
                // retirer la référence inverse
                child.setRoom(null);
            }
            entity.getAppointments().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Room> saveAll(List<Room> roomList) {

        return roomRepository.saveAll(roomList);
    }

}
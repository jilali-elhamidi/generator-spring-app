package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Shift;
import com.example.modules.entertainment_ecosystem.repository.ShiftRepository;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ShiftService extends BaseService<Shift> {

    protected final ShiftRepository shiftRepository;
    private final EmployeeRepository employeeRepository;

    public ShiftService(ShiftRepository repository,EmployeeRepository employeeRepository)
    {
        super(repository);
        this.shiftRepository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Shift save(Shift shift) {


    


    

    if (shift.getEmployee() != null
        && shift.getEmployee().getId() != null) {
        Employee existingEmployee = employeeRepository.findById(
        shift.getEmployee().getId()
        ).orElseThrow(() -> new RuntimeException("Employee not found"));
        shift.setEmployee(existingEmployee);
        }
    

        return shiftRepository.save(shift);
    }


    public Shift update(Long id, Shift shiftRequest) {
        Shift existing = shiftRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Shift not found"));

    // Copier les champs simples
        existing.setShiftDate(shiftRequest.getShiftDate());
        existing.setStartTime(shiftRequest.getStartTime());
        existing.setEndTime(shiftRequest.getEndTime());

// Relations ManyToOne : mise à jour conditionnelle
        if (shiftRequest.getEmployee() != null &&
        shiftRequest.getEmployee().getId() != null) {

        Employee existingEmployee = employeeRepository.findById(
        shiftRequest.getEmployee().getId()
        ).orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setEmployee(existingEmployee);
        } else {
        existing.setEmployee(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    


        return shiftRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Shift> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Shift entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    
        if (entity.getEmployee() != null) {
        entity.setEmployee(null);
        }
    


repository.delete(entity);
return true;
}
}
package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.repository.EmployeeRepository;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.repository.ProductionCompanyRepository;
import com.example.modules.entertainment_ecosystem.model.Shift;
import com.example.modules.entertainment_ecosystem.repository.ShiftRepository;
import com.example.modules.entertainment_ecosystem.model.EventLocation;
import com.example.modules.entertainment_ecosystem.repository.EventLocationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EmployeeService extends BaseService<Employee> {

    protected final EmployeeRepository employeeRepository;
    private final ProductionCompanyRepository productionCompanyRepository;
    private final ShiftRepository shiftsRepository;
    private final EventLocationRepository managedLocationsRepository;

    public EmployeeService(EmployeeRepository repository,ProductionCompanyRepository productionCompanyRepository,ShiftRepository shiftsRepository,EventLocationRepository managedLocationsRepository)
    {
        super(repository);
        this.employeeRepository = repository;
        this.productionCompanyRepository = productionCompanyRepository;
        this.shiftsRepository = shiftsRepository;
        this.managedLocationsRepository = managedLocationsRepository;
    }

    @Override
    public Employee save(Employee employee) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (employee.getShifts() != null) {
            List<Shift> managedShifts = new ArrayList<>();
            for (Shift item : employee.getShifts()) {
            if (item.getId() != null) {
            Shift existingItem = shiftsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Shift not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setEmployee(employee);
            managedShifts.add(existingItem);
            } else {
            item.setEmployee(employee);
            managedShifts.add(item);
            }
            }
            employee.setShifts(managedShifts);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (employee.getManagedLocations() != null) {
            List<EventLocation> managedManagedLocations = new ArrayList<>();
            for (EventLocation item : employee.getManagedLocations()) {
            if (item.getId() != null) {
            EventLocation existingItem = managedLocationsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("EventLocation not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setContactPerson(employee);
            managedManagedLocations.add(existingItem);
            } else {
            item.setContactPerson(employee);
            managedManagedLocations.add(item);
            }
            }
            employee.setManagedLocations(managedManagedLocations);
            }
        
    

    if (employee.getProductionCompany() != null
        && employee.getProductionCompany().getId() != null) {
        ProductionCompany existingProductionCompany = productionCompanyRepository.findById(
        employee.getProductionCompany().getId()
        ).orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        employee.setProductionCompany(existingProductionCompany);
        }
    
    
    

        return employeeRepository.save(employee);
    }


    public Employee update(Long id, Employee employeeRequest) {
        Employee existing = employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    // Copier les champs simples
        existing.setFirstName(employeeRequest.getFirstName());
        existing.setLastName(employeeRequest.getLastName());
        existing.setEmail(employeeRequest.getEmail());
        existing.setPosition(employeeRequest.getPosition());

// Relations ManyToOne : mise à jour conditionnelle
        if (employeeRequest.getProductionCompany() != null &&
        employeeRequest.getProductionCompany().getId() != null) {

        ProductionCompany existingProductionCompany = productionCompanyRepository.findById(
        employeeRequest.getProductionCompany().getId()
        ).orElseThrow(() -> new RuntimeException("ProductionCompany not found"));

        existing.setProductionCompany(existingProductionCompany);
        } else {
        existing.setProductionCompany(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getShifts().clear();

        if (employeeRequest.getShifts() != null) {
        for (var item : employeeRequest.getShifts()) {
        Shift existingItem;
        if (item.getId() != null) {
        existingItem = shiftsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Shift not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setEmployee(existing);

        // Ajouter directement dans la collection existante
        existing.getShifts().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getManagedLocations().clear();

        if (employeeRequest.getManagedLocations() != null) {
        for (var item : employeeRequest.getManagedLocations()) {
        EventLocation existingItem;
        if (item.getId() != null) {
        existingItem = managedLocationsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("EventLocation not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setContactPerson(existing);

        // Ajouter directement dans la collection existante
        existing.getManagedLocations().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    


        return employeeRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Employee> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Employee entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    
        if (entity.getShifts() != null) {
        for (var child : entity.getShifts()) {
        
            child.setEmployee(null); // retirer la référence inverse
        
        }
        entity.getShifts().clear();
        }
    

    
        if (entity.getManagedLocations() != null) {
        for (var child : entity.getManagedLocations()) {
        
            child.setContactPerson(null); // retirer la référence inverse
        
        }
        entity.getManagedLocations().clear();
        }
    


// --- Dissocier ManyToMany ---

    

    

    


// --- Dissocier OneToOne ---

    

    

    


// --- Dissocier ManyToOne ---

    
        if (entity.getProductionCompany() != null) {
        entity.setProductionCompany(null);
        }
    

    

    


repository.delete(entity);
return true;
}
}
package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.EmployeeRole;
import com.example.modules.entertainment_ecosystem.repository.EmployeeRoleRepository;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class EmployeeRoleService extends BaseService<EmployeeRole> {

    protected final EmployeeRoleRepository employeeroleRepository;
    private final EmployeeRepository employeesRepository;

    public EmployeeRoleService(EmployeeRoleRepository repository, EmployeeRepository employeesRepository)
    {
        super(repository);
        this.employeeroleRepository = repository;
        this.employeesRepository = employeesRepository;
    }

    @Override
    public EmployeeRole save(EmployeeRole employeerole) {
    // ---------- OneToMany ----------
        if (employeerole.getEmployees() != null) {
            List<Employee> managedEmployees = new ArrayList<>();
            for (Employee item : employeerole.getEmployees()) {
                if (item.getId() != null) {
                    Employee existingItem = employeesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Employee not found"));

                     existingItem.setRole(employeerole);
                     managedEmployees.add(existingItem);
                } else {
                    item.setRole(employeerole);
                    managedEmployees.add(item);
                }
            }
            employeerole.setEmployees(managedEmployees);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return employeeroleRepository.save(employeerole);
}


    public EmployeeRole update(Long id, EmployeeRole employeeroleRequest) {
        EmployeeRole existing = employeeroleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("EmployeeRole not found"));

    // Copier les champs simples
        existing.setName(employeeroleRequest.getName());
        existing.setDescription(employeeroleRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getEmployees().clear();

        if (employeeroleRequest.getEmployees() != null) {
            for (var item : employeeroleRequest.getEmployees()) {
                Employee existingItem;
                if (item.getId() != null) {
                    existingItem = employeesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Employee not found"));
                } else {
                existingItem = item;
                }

                existingItem.setRole(existing);
                existing.getEmployees().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return employeeroleRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<EmployeeRole> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        EmployeeRole entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getEmployees() != null) {
            for (var child : entity.getEmployees()) {
                // retirer la référence inverse
                child.setRole(null);
            }
            entity.getEmployees().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<EmployeeRole> saveAll(List<EmployeeRole> employeeroleList) {

        return employeeroleRepository.saveAll(employeeroleList);
    }

}
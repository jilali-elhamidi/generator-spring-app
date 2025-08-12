package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.repository.EmployeeRepository;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.repository.ProductionCompanyRepository;
import com.example.modules.entertainment_ecosystem.model.Shift;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class EmployeeService extends BaseService<Employee> {

    protected final EmployeeRepository employeeRepository;
    private final ProductionCompanyRepository productionCompanyRepository;

    public EmployeeService(EmployeeRepository repository,ProductionCompanyRepository productionCompanyRepository)
    {
        super(repository);
        this.employeeRepository = repository;
        this.productionCompanyRepository = productionCompanyRepository;
    }

    @Override
    public Employee save(Employee employee) {

        if (employee.getProductionCompany() != null && employee.getProductionCompany().getId() != null) {
        ProductionCompany productionCompany = productionCompanyRepository.findById(employee.getProductionCompany().getId())
                .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        employee.setProductionCompany(productionCompany);
        }

        if (employee.getShifts() != null) {
            for (Shift item : employee.getShifts()) {
            item.setEmployee(employee);
            }
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

        if (employeeRequest.getProductionCompany() != null && employeeRequest.getProductionCompany().getId() != null) {
        ProductionCompany productionCompany = productionCompanyRepository.findById(employeeRequest.getProductionCompany().getId())
                .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));
        existing.setProductionCompany(productionCompany);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getShifts().clear();
        if (employeeRequest.getShifts() != null) {
            for (var item : employeeRequest.getShifts()) {
            item.setEmployee(existing);
            existing.getShifts().add(item);
            }
        }

        return employeeRepository.save(existing);
    }
}
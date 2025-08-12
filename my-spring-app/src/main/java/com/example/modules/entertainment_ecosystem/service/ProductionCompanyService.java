package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.repository.ProductionCompanyRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.model.Employee;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class ProductionCompanyService extends BaseService<ProductionCompany> {

    protected final ProductionCompanyRepository productioncompanyRepository;

    public ProductionCompanyService(ProductionCompanyRepository repository)
    {
        super(repository);
        this.productioncompanyRepository = repository;
    }

    @Override
    public ProductionCompany save(ProductionCompany productioncompany) {

        if (productioncompany.getMovies() != null) {
            for (Movie item : productioncompany.getMovies()) {
            item.setProductionCompany(productioncompany);
            }
        }

        if (productioncompany.getTvShows() != null) {
            for (TVShow item : productioncompany.getTvShows()) {
            item.setProductionCompany(productioncompany);
            }
        }

        if (productioncompany.getStaff() != null) {
            for (Employee item : productioncompany.getStaff()) {
            item.setProductionCompany(productioncompany);
            }
        }

        return productioncompanyRepository.save(productioncompany);
    }


    public ProductionCompany update(Long id, ProductionCompany productioncompanyRequest) {
        ProductionCompany existing = productioncompanyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));

    // Copier les champs simples
        existing.setName(productioncompanyRequest.getName());
        existing.setFoundedDate(productioncompanyRequest.getFoundedDate());
        existing.setLocation(productioncompanyRequest.getLocation());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getMovies().clear();
        if (productioncompanyRequest.getMovies() != null) {
            for (var item : productioncompanyRequest.getMovies()) {
            item.setProductionCompany(existing);
            existing.getMovies().add(item);
            }
        }

        existing.getTvShows().clear();
        if (productioncompanyRequest.getTvShows() != null) {
            for (var item : productioncompanyRequest.getTvShows()) {
            item.setProductionCompany(existing);
            existing.getTvShows().add(item);
            }
        }

        existing.getStaff().clear();
        if (productioncompanyRequest.getStaff() != null) {
            for (var item : productioncompanyRequest.getStaff()) {
            item.setProductionCompany(existing);
            existing.getStaff().add(item);
            }
        }

        return productioncompanyRepository.save(existing);
    }
}
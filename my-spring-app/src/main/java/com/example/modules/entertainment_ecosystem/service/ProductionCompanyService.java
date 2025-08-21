package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ProductionCompany;
import com.example.modules.entertainment_ecosystem.repository.ProductionCompanyRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.Employee;
import com.example.modules.entertainment_ecosystem.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ProductionCompanyService extends BaseService<ProductionCompany> {

    protected final ProductionCompanyRepository productioncompanyRepository;
    private final MovieRepository moviesRepository;
    private final TVShowRepository tvShowsRepository;
    private final EmployeeRepository staffRepository;

    public ProductionCompanyService(ProductionCompanyRepository repository, MovieRepository moviesRepository, TVShowRepository tvShowsRepository, EmployeeRepository staffRepository)
    {
        super(repository);
        this.productioncompanyRepository = repository;
        this.moviesRepository = moviesRepository;
        this.tvShowsRepository = tvShowsRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public ProductionCompany save(ProductionCompany productioncompany) {
    // ---------- OneToMany ----------
        if (productioncompany.getMovies() != null) {
            List<Movie> managedMovies = new ArrayList<>();
            for (Movie item : productioncompany.getMovies()) {
                if (item.getId() != null) {
                    Movie existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));

                     existingItem.setProductionCompany(productioncompany);
                     managedMovies.add(existingItem);
                } else {
                    item.setProductionCompany(productioncompany);
                    managedMovies.add(item);
                }
            }
            productioncompany.setMovies(managedMovies);
        }
    
        if (productioncompany.getTvShows() != null) {
            List<TVShow> managedTvShows = new ArrayList<>();
            for (TVShow item : productioncompany.getTvShows()) {
                if (item.getId() != null) {
                    TVShow existingItem = tvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));

                     existingItem.setProductionCompany(productioncompany);
                     managedTvShows.add(existingItem);
                } else {
                    item.setProductionCompany(productioncompany);
                    managedTvShows.add(item);
                }
            }
            productioncompany.setTvShows(managedTvShows);
        }
    
        if (productioncompany.getStaff() != null) {
            List<Employee> managedStaff = new ArrayList<>();
            for (Employee item : productioncompany.getStaff()) {
                if (item.getId() != null) {
                    Employee existingItem = staffRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Employee not found"));

                     existingItem.setProductionCompany(productioncompany);
                     managedStaff.add(existingItem);
                } else {
                    item.setProductionCompany(productioncompany);
                    managedStaff.add(item);
                }
            }
            productioncompany.setStaff(managedStaff);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return productioncompanyRepository.save(productioncompany);
}


    public ProductionCompany update(Long id, ProductionCompany productioncompanyRequest) {
        ProductionCompany existing = productioncompanyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ProductionCompany not found"));

    // Copier les champs simples
        existing.setName(productioncompanyRequest.getName());
        existing.setFoundedDate(productioncompanyRequest.getFoundedDate());
        existing.setLocation(productioncompanyRequest.getLocation());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getMovies().clear();

        if (productioncompanyRequest.getMovies() != null) {
            for (var item : productioncompanyRequest.getMovies()) {
                Movie existingItem;
                if (item.getId() != null) {
                    existingItem = moviesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Movie not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProductionCompany(existing);
                existing.getMovies().add(existingItem);
            }
        }
        
        existing.getTvShows().clear();

        if (productioncompanyRequest.getTvShows() != null) {
            for (var item : productioncompanyRequest.getTvShows()) {
                TVShow existingItem;
                if (item.getId() != null) {
                    existingItem = tvShowsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("TVShow not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProductionCompany(existing);
                existing.getTvShows().add(existingItem);
            }
        }
        
        existing.getStaff().clear();

        if (productioncompanyRequest.getStaff() != null) {
            for (var item : productioncompanyRequest.getStaff()) {
                Employee existingItem;
                if (item.getId() != null) {
                    existingItem = staffRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Employee not found"));
                } else {
                existingItem = item;
                }

                existingItem.setProductionCompany(existing);
                existing.getStaff().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return productioncompanyRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ProductionCompany> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ProductionCompany entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getMovies() != null) {
            for (var child : entity.getMovies()) {
                // retirer la référence inverse
                child.setProductionCompany(null);
            }
            entity.getMovies().clear();
        }
        
        if (entity.getTvShows() != null) {
            for (var child : entity.getTvShows()) {
                // retirer la référence inverse
                child.setProductionCompany(null);
            }
            entity.getTvShows().clear();
        }
        
        if (entity.getStaff() != null) {
            for (var child : entity.getStaff()) {
                // retirer la référence inverse
                child.setProductionCompany(null);
            }
            entity.getStaff().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}
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

    public ProductionCompanyService(ProductionCompanyRepository repository,MovieRepository moviesRepository,TVShowRepository tvShowsRepository,EmployeeRepository staffRepository)
    {
        super(repository);
        this.productioncompanyRepository = repository;
        this.moviesRepository = moviesRepository;
        this.tvShowsRepository = tvShowsRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public ProductionCompany save(ProductionCompany productioncompany) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (productioncompany.getMovies() != null) {
            List<Movie> managedMovies = new ArrayList<>();
            for (Movie item : productioncompany.getMovies()) {
            if (item.getId() != null) {
            Movie existingItem = moviesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Movie not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProductionCompany(productioncompany);
            managedMovies.add(existingItem);
            } else {
            item.setProductionCompany(productioncompany);
            managedMovies.add(item);
            }
            }
            productioncompany.setMovies(managedMovies);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (productioncompany.getTvShows() != null) {
            List<TVShow> managedTvShows = new ArrayList<>();
            for (TVShow item : productioncompany.getTvShows()) {
            if (item.getId() != null) {
            TVShow existingItem = tvShowsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("TVShow not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProductionCompany(productioncompany);
            managedTvShows.add(existingItem);
            } else {
            item.setProductionCompany(productioncompany);
            managedTvShows.add(item);
            }
            }
            productioncompany.setTvShows(managedTvShows);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (productioncompany.getStaff() != null) {
            List<Employee> managedStaff = new ArrayList<>();
            for (Employee item : productioncompany.getStaff()) {
            if (item.getId() != null) {
            Employee existingItem = staffRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setProductionCompany(productioncompany);
            managedStaff.add(existingItem);
            } else {
            item.setProductionCompany(productioncompany);
            managedStaff.add(item);
            }
            }
            productioncompany.setStaff(managedStaff);
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
        List<Movie> managedMovies = new ArrayList<>();

        for (var item : productioncompanyRequest.getMovies()) {
        if (item.getId() != null) {
        Movie existingItem = moviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        existingItem.setProductionCompany(existing);
        managedMovies.add(existingItem);
        } else {
        item.setProductionCompany(existing);
        managedMovies.add(item);
        }
        }
        existing.setMovies(managedMovies);
        }
        existing.getTvShows().clear();

        if (productioncompanyRequest.getTvShows() != null) {
        List<TVShow> managedTvShows = new ArrayList<>();

        for (var item : productioncompanyRequest.getTvShows()) {
        if (item.getId() != null) {
        TVShow existingItem = tvShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found"));
        existingItem.setProductionCompany(existing);
        managedTvShows.add(existingItem);
        } else {
        item.setProductionCompany(existing);
        managedTvShows.add(item);
        }
        }
        existing.setTvShows(managedTvShows);
        }
        existing.getStaff().clear();

        if (productioncompanyRequest.getStaff() != null) {
        List<Employee> managedStaff = new ArrayList<>();

        for (var item : productioncompanyRequest.getStaff()) {
        if (item.getId() != null) {
        Employee existingItem = staffRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Employee not found"));
        existingItem.setProductionCompany(existing);
        managedStaff.add(existingItem);
        } else {
        item.setProductionCompany(existing);
        managedStaff.add(item);
        }
        }
        existing.setStaff(managedStaff);
        }

    

    

    


        return productioncompanyRepository.save(existing);
    }


}
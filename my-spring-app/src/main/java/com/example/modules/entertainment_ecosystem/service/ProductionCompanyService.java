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
        // Vider la collection existante
        existing.getMovies().clear();

        if (productioncompanyRequest.getMovies() != null) {
        for (var item : productioncompanyRequest.getMovies()) {
        Movie existingItem;
        if (item.getId() != null) {
        existingItem = moviesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Movie not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProductionCompany(existing);

        // Ajouter directement dans la collection existante
        existing.getMovies().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getTvShows().clear();

        if (productioncompanyRequest.getTvShows() != null) {
        for (var item : productioncompanyRequest.getTvShows()) {
        TVShow existingItem;
        if (item.getId() != null) {
        existingItem = tvShowsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("TVShow not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProductionCompany(existing);

        // Ajouter directement dans la collection existante
        existing.getTvShows().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getStaff().clear();

        if (productioncompanyRequest.getStaff() != null) {
        for (var item : productioncompanyRequest.getStaff()) {
        Employee existingItem;
        if (item.getId() != null) {
        existingItem = staffRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Employee not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setProductionCompany(existing);

        // Ajouter directement dans la collection existante
        existing.getStaff().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    


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
        
            child.setProductionCompany(null); // retirer la référence inverse
        
        }
        entity.getMovies().clear();
        }
    

    
        if (entity.getTvShows() != null) {
        for (var child : entity.getTvShows()) {
        
            child.setProductionCompany(null); // retirer la référence inverse
        
        }
        entity.getTvShows().clear();
        }
    

    
        if (entity.getStaff() != null) {
        for (var child : entity.getStaff()) {
        
            child.setProductionCompany(null); // retirer la référence inverse
        
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
package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;
import com.example.modules.entertainment_ecosystem.model.TVShow;
import com.example.modules.entertainment_ecosystem.repository.TVShowRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseTypeRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseInventory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseInventoryRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseSupplierRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MerchandiseService extends BaseService<Merchandise> {

    protected final MerchandiseRepository merchandiseRepository;
    private final ArtistRepository artistRepository;
    private final MovieRepository relatedMoviesRepository;
    private final TVShowRepository relatedShowsRepository;
    private final UserProfileRepository ownedByUsersRepository;
    private final MerchandiseTypeRepository productTypeRepository;
    private final MerchandiseInventoryRepository inventoryRepository;
    private final MerchandiseSupplierRepository supplierRepository;

    public MerchandiseService(MerchandiseRepository repository,ArtistRepository artistRepository,MovieRepository relatedMoviesRepository,TVShowRepository relatedShowsRepository,UserProfileRepository ownedByUsersRepository,MerchandiseTypeRepository productTypeRepository,MerchandiseInventoryRepository inventoryRepository,MerchandiseSupplierRepository supplierRepository)
    {
        super(repository);
        this.merchandiseRepository = repository;
        this.artistRepository = artistRepository;
        this.relatedMoviesRepository = relatedMoviesRepository;
        this.relatedShowsRepository = relatedShowsRepository;
        this.ownedByUsersRepository = ownedByUsersRepository;
        this.productTypeRepository = productTypeRepository;
            this.inventoryRepository = inventoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Merchandise save(Merchandise merchandise) {

        if (merchandise.getArtist() != null && merchandise.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(merchandise.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        merchandise.setArtist(artist);
        }

        if (merchandise.getProductType() != null && merchandise.getProductType().getId() != null) {
        MerchandiseType productType = productTypeRepository.findById(merchandise.getProductType().getId())
                .orElseThrow(() -> new RuntimeException("MerchandiseType not found"));
        merchandise.setProductType(productType);
        }

        if (merchandise.getSupplier() != null && merchandise.getSupplier().getId() != null) {
        MerchandiseSupplier supplier = supplierRepository.findById(merchandise.getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("MerchandiseSupplier not found"));
        merchandise.setSupplier(supplier);
        }

        if (merchandise.getReviews() != null) {
            for (MerchandiseReview item : merchandise.getReviews()) {
            item.setMerchandise(merchandise);
            }
        }

        if (merchandise.getSales() != null) {
            for (MerchandiseSale item : merchandise.getSales()) {
            item.setMerchandiseItem(merchandise);
            }
        }

        if (merchandise.getShipments() != null) {
            for (MerchandiseShipping item : merchandise.getShipments()) {
            item.setMerchandiseItem(merchandise);
            }
        }

        if (merchandise.getOrderItems() != null) {
            for (MerchandiseOrderItem item : merchandise.getOrderItems()) {
            item.setMerchandiseItem(merchandise);
            }
        }
        if (merchandise.getInventory() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            merchandise.setInventory(
            inventoryRepository.findById(merchandise.getInventory().getId())
            .orElseThrow(() -> new RuntimeException("inventory not found"))
            );
        
        merchandise.getInventory().setMerchandiseItem(merchandise);
        }

        return merchandiseRepository.save(merchandise);
    }


    public Merchandise update(Long id, Merchandise merchandiseRequest) {
        Merchandise existing = merchandiseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Merchandise not found"));

    // Copier les champs simples
        existing.setName(merchandiseRequest.getName());
        existing.setDescription(merchandiseRequest.getDescription());
        existing.setPrice(merchandiseRequest.getPrice());

// Relations ManyToOne : mise à jour conditionnelle

        if (merchandiseRequest.getArtist() != null && merchandiseRequest.getArtist().getId() != null) {
        Artist artist = artistRepository.findById(merchandiseRequest.getArtist().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setArtist(artist);
        }

        if (merchandiseRequest.getProductType() != null && merchandiseRequest.getProductType().getId() != null) {
        MerchandiseType productType = productTypeRepository.findById(merchandiseRequest.getProductType().getId())
                .orElseThrow(() -> new RuntimeException("MerchandiseType not found"));
        existing.setProductType(productType);
        }

        if (merchandiseRequest.getSupplier() != null && merchandiseRequest.getSupplier().getId() != null) {
        MerchandiseSupplier supplier = supplierRepository.findById(merchandiseRequest.getSupplier().getId())
                .orElseThrow(() -> new RuntimeException("MerchandiseSupplier not found"));
        existing.setSupplier(supplier);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (merchandiseRequest.getRelatedMovies() != null) {
            existing.getRelatedMovies().clear();
            List<Movie> relatedMoviesList = merchandiseRequest.getRelatedMovies().stream()
                .map(item -> relatedMoviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());
        existing.getRelatedMovies().addAll(relatedMoviesList);
        }

        if (merchandiseRequest.getRelatedShows() != null) {
            existing.getRelatedShows().clear();
            List<TVShow> relatedShowsList = merchandiseRequest.getRelatedShows().stream()
                .map(item -> relatedShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());
        existing.getRelatedShows().addAll(relatedShowsList);
        }

        if (merchandiseRequest.getOwnedByUsers() != null) {
            existing.getOwnedByUsers().clear();
            List<UserProfile> ownedByUsersList = merchandiseRequest.getOwnedByUsers().stream()
                .map(item -> ownedByUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getOwnedByUsers().addAll(ownedByUsersList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getReviews().clear();
        if (merchandiseRequest.getReviews() != null) {
            for (var item : merchandiseRequest.getReviews()) {
            item.setMerchandise(existing);
            existing.getReviews().add(item);
            }
        }

        existing.getSales().clear();
        if (merchandiseRequest.getSales() != null) {
            for (var item : merchandiseRequest.getSales()) {
            item.setMerchandiseItem(existing);
            existing.getSales().add(item);
            }
        }

        existing.getShipments().clear();
        if (merchandiseRequest.getShipments() != null) {
            for (var item : merchandiseRequest.getShipments()) {
            item.setMerchandiseItem(existing);
            existing.getShipments().add(item);
            }
        }

        existing.getOrderItems().clear();
        if (merchandiseRequest.getOrderItems() != null) {
            for (var item : merchandiseRequest.getOrderItems()) {
            item.setMerchandiseItem(existing);
            existing.getOrderItems().add(item);
            }
        }

    

    

    

    

    

    

        if (merchandiseRequest.getInventory() != null
        && merchandiseRequest.getInventory().getId() != null) {

        MerchandiseInventory inventory = inventoryRepository.findById(
        merchandiseRequest.getInventory().getId()
        ).orElseThrow(() -> new RuntimeException("MerchandiseInventory not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setInventory(inventory);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            inventory.setMerchandiseItem(existing);
        
        }

    

    

    

    

    

    


        return merchandiseRepository.save(existing);
    }
}
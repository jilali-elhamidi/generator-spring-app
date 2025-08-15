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
import com.example.modules.entertainment_ecosystem.repository.MerchandiseReviewRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSale;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseSaleRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseSupplier;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseSupplierRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderItemRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseService extends BaseService<Merchandise> {

    protected final MerchandiseRepository merchandiseRepository;
    private final ArtistRepository artistRepository;
    private final MovieRepository relatedMoviesRepository;
    private final TVShowRepository relatedShowsRepository;
    private final UserProfileRepository ownedByUsersRepository;
    private final MerchandiseTypeRepository productTypeRepository;
    private final MerchandiseInventoryRepository inventoryRepository;
    private final MerchandiseReviewRepository reviewsRepository;
    private final MerchandiseSaleRepository salesRepository;
    private final MerchandiseSupplierRepository supplierRepository;
    private final MerchandiseShippingRepository shipmentsRepository;
    private final MerchandiseOrderItemRepository orderItemsRepository;

    public MerchandiseService(MerchandiseRepository repository,ArtistRepository artistRepository,MovieRepository relatedMoviesRepository,TVShowRepository relatedShowsRepository,UserProfileRepository ownedByUsersRepository,MerchandiseTypeRepository productTypeRepository,MerchandiseInventoryRepository inventoryRepository,MerchandiseReviewRepository reviewsRepository,MerchandiseSaleRepository salesRepository,MerchandiseSupplierRepository supplierRepository,MerchandiseShippingRepository shipmentsRepository,MerchandiseOrderItemRepository orderItemsRepository)
    {
        super(repository);
        this.merchandiseRepository = repository;
        this.artistRepository = artistRepository;
        this.relatedMoviesRepository = relatedMoviesRepository;
        this.relatedShowsRepository = relatedShowsRepository;
        this.ownedByUsersRepository = ownedByUsersRepository;
        this.productTypeRepository = productTypeRepository;
        this.inventoryRepository = inventoryRepository;
        this.reviewsRepository = reviewsRepository;
        this.salesRepository = salesRepository;
        this.supplierRepository = supplierRepository;
        this.shipmentsRepository = shipmentsRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    @Override
    public Merchandise save(Merchandise merchandise) {


    

    

    

    

    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (merchandise.getReviews() != null) {
            List<MerchandiseReview> managedReviews = new ArrayList<>();
            for (MerchandiseReview item : merchandise.getReviews()) {
            if (item.getId() != null) {
            MerchandiseReview existingItem = reviewsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMerchandise(merchandise);
            managedReviews.add(existingItem);
            } else {
            item.setMerchandise(merchandise);
            managedReviews.add(item);
            }
            }
            merchandise.setReviews(managedReviews);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (merchandise.getSales() != null) {
            List<MerchandiseSale> managedSales = new ArrayList<>();
            for (MerchandiseSale item : merchandise.getSales()) {
            if (item.getId() != null) {
            MerchandiseSale existingItem = salesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMerchandiseItem(merchandise);
            managedSales.add(existingItem);
            } else {
            item.setMerchandiseItem(merchandise);
            managedSales.add(item);
            }
            }
            merchandise.setSales(managedSales);
            }
        
    

    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (merchandise.getShipments() != null) {
            List<MerchandiseShipping> managedShipments = new ArrayList<>();
            for (MerchandiseShipping item : merchandise.getShipments()) {
            if (item.getId() != null) {
            MerchandiseShipping existingItem = shipmentsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMerchandiseItem(merchandise);
            managedShipments.add(existingItem);
            } else {
            item.setMerchandiseItem(merchandise);
            managedShipments.add(item);
            }
            }
            merchandise.setShipments(managedShipments);
            }
        
    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (merchandise.getOrderItems() != null) {
            List<MerchandiseOrderItem> managedOrderItems = new ArrayList<>();
            for (MerchandiseOrderItem item : merchandise.getOrderItems()) {
            if (item.getId() != null) {
            MerchandiseOrderItem existingItem = orderItemsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MerchandiseOrderItem not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setMerchandiseItem(merchandise);
            managedOrderItems.add(existingItem);
            } else {
            item.setMerchandiseItem(merchandise);
            managedOrderItems.add(item);
            }
            }
            merchandise.setOrderItems(managedOrderItems);
            }
        
    

    if (merchandise.getArtist() != null
        && merchandise.getArtist().getId() != null) {
        Artist existingArtist = artistRepository.findById(
        merchandise.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));
        merchandise.setArtist(existingArtist);
        }
    
    
    
    
    if (merchandise.getProductType() != null
        && merchandise.getProductType().getId() != null) {
        MerchandiseType existingProductType = productTypeRepository.findById(
        merchandise.getProductType().getId()
        ).orElseThrow(() -> new RuntimeException("MerchandiseType not found"));
        merchandise.setProductType(existingProductType);
        }
    
    
    
    
    if (merchandise.getSupplier() != null
        && merchandise.getSupplier().getId() != null) {
        MerchandiseSupplier existingSupplier = supplierRepository.findById(
        merchandise.getSupplier().getId()
        ).orElseThrow(() -> new RuntimeException("MerchandiseSupplier not found"));
        merchandise.setSupplier(existingSupplier);
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
        if (merchandiseRequest.getArtist() != null &&
        merchandiseRequest.getArtist().getId() != null) {

        Artist existingArtist = artistRepository.findById(
        merchandiseRequest.getArtist().getId()
        ).orElseThrow(() -> new RuntimeException("Artist not found"));

        existing.setArtist(existingArtist);
        } else {
        existing.setArtist(null);
        }
        if (merchandiseRequest.getProductType() != null &&
        merchandiseRequest.getProductType().getId() != null) {

        MerchandiseType existingProductType = productTypeRepository.findById(
        merchandiseRequest.getProductType().getId()
        ).orElseThrow(() -> new RuntimeException("MerchandiseType not found"));

        existing.setProductType(existingProductType);
        } else {
        existing.setProductType(null);
        }
        if (merchandiseRequest.getSupplier() != null &&
        merchandiseRequest.getSupplier().getId() != null) {

        MerchandiseSupplier existingSupplier = supplierRepository.findById(
        merchandiseRequest.getSupplier().getId()
        ).orElseThrow(() -> new RuntimeException("MerchandiseSupplier not found"));

        existing.setSupplier(existingSupplier);
        } else {
        existing.setSupplier(null);
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
        // Vider la collection existante
        existing.getReviews().clear();

        if (merchandiseRequest.getReviews() != null) {
        for (var item : merchandiseRequest.getReviews()) {
        MerchandiseReview existingItem;
        if (item.getId() != null) {
        existingItem = reviewsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMerchandise(existing);

        // Ajouter directement dans la collection existante
        existing.getReviews().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getSales().clear();

        if (merchandiseRequest.getSales() != null) {
        for (var item : merchandiseRequest.getSales()) {
        MerchandiseSale existingItem;
        if (item.getId() != null) {
        existingItem = salesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMerchandiseItem(existing);

        // Ajouter directement dans la collection existante
        existing.getSales().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getShipments().clear();

        if (merchandiseRequest.getShipments() != null) {
        for (var item : merchandiseRequest.getShipments()) {
        MerchandiseShipping existingItem;
        if (item.getId() != null) {
        existingItem = shipmentsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMerchandiseItem(existing);

        // Ajouter directement dans la collection existante
        existing.getShipments().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()
        // Vider la collection existante
        existing.getOrderItems().clear();

        if (merchandiseRequest.getOrderItems() != null) {
        for (var item : merchandiseRequest.getOrderItems()) {
        MerchandiseOrderItem existingItem;
        if (item.getId() != null) {
        existingItem = orderItemsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseOrderItem not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setMerchandiseItem(existing);

        // Ajouter directement dans la collection existante
        existing.getOrderItems().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

    

    

    

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
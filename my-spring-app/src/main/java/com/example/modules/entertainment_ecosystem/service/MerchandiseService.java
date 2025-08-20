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
import com.example.modules.entertainment_ecosystem.model.MerchandiseCollection;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseCollectionRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseCategory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseCategoryRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseStockHistory;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseStockHistoryRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final MerchandiseCollectionRepository collectionRepository;
    private final MerchandiseCategoryRepository categoryRepository;
    private final MerchandiseStockHistoryRepository stockHistoryRepository;

    public MerchandiseService(MerchandiseRepository repository, ArtistRepository artistRepository, MovieRepository relatedMoviesRepository, TVShowRepository relatedShowsRepository, UserProfileRepository ownedByUsersRepository, MerchandiseTypeRepository productTypeRepository, MerchandiseInventoryRepository inventoryRepository, MerchandiseReviewRepository reviewsRepository, MerchandiseSaleRepository salesRepository, MerchandiseSupplierRepository supplierRepository, MerchandiseShippingRepository shipmentsRepository, MerchandiseOrderItemRepository orderItemsRepository, MerchandiseCollectionRepository collectionRepository, MerchandiseCategoryRepository categoryRepository, MerchandiseStockHistoryRepository stockHistoryRepository)
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
        this.collectionRepository = collectionRepository;
        this.categoryRepository = categoryRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    @Override
    public Merchandise save(Merchandise merchandise) {
    // ---------- OneToMany ----------
        if (merchandise.getReviews() != null) {
            List<MerchandiseReview> managedReviews = new ArrayList<>();
            for (MerchandiseReview item : merchandise.getReviews()) {
                if (item.getId() != null) {
                    MerchandiseReview existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));

                     existingItem.setMerchandise(merchandise);
                     managedReviews.add(existingItem);
                } else {
                    item.setMerchandise(merchandise);
                    managedReviews.add(item);
                }
            }
            merchandise.setReviews(managedReviews);
        }
    
        if (merchandise.getSales() != null) {
            List<MerchandiseSale> managedSales = new ArrayList<>();
            for (MerchandiseSale item : merchandise.getSales()) {
                if (item.getId() != null) {
                    MerchandiseSale existingItem = salesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));

                     existingItem.setMerchandiseItem(merchandise);
                     managedSales.add(existingItem);
                } else {
                    item.setMerchandiseItem(merchandise);
                    managedSales.add(item);
                }
            }
            merchandise.setSales(managedSales);
        }
    
        if (merchandise.getShipments() != null) {
            List<MerchandiseShipping> managedShipments = new ArrayList<>();
            for (MerchandiseShipping item : merchandise.getShipments()) {
                if (item.getId() != null) {
                    MerchandiseShipping existingItem = shipmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));

                     existingItem.setMerchandiseItem(merchandise);
                     managedShipments.add(existingItem);
                } else {
                    item.setMerchandiseItem(merchandise);
                    managedShipments.add(item);
                }
            }
            merchandise.setShipments(managedShipments);
        }
    
        if (merchandise.getOrderItems() != null) {
            List<MerchandiseOrderItem> managedOrderItems = new ArrayList<>();
            for (MerchandiseOrderItem item : merchandise.getOrderItems()) {
                if (item.getId() != null) {
                    MerchandiseOrderItem existingItem = orderItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseOrderItem not found"));

                     existingItem.setMerchandiseItem(merchandise);
                     managedOrderItems.add(existingItem);
                } else {
                    item.setMerchandiseItem(merchandise);
                    managedOrderItems.add(item);
                }
            }
            merchandise.setOrderItems(managedOrderItems);
        }
    
        if (merchandise.getStockHistory() != null) {
            List<MerchandiseStockHistory> managedStockHistory = new ArrayList<>();
            for (MerchandiseStockHistory item : merchandise.getStockHistory()) {
                if (item.getId() != null) {
                    MerchandiseStockHistory existingItem = stockHistoryRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseStockHistory not found"));

                     existingItem.setMerchandiseItem(merchandise);
                     managedStockHistory.add(existingItem);
                } else {
                    item.setMerchandiseItem(merchandise);
                    managedStockHistory.add(item);
                }
            }
            merchandise.setStockHistory(managedStockHistory);
        }
    
    // ---------- ManyToMany ----------
        if (merchandise.getRelatedMovies() != null &&
            !merchandise.getRelatedMovies().isEmpty()) {

            List<Movie> attachedRelatedMovies = merchandise.getRelatedMovies().stream()
            .map(item -> relatedMoviesRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + item.getId())))
            .toList();

            merchandise.setRelatedMovies(attachedRelatedMovies);

            // côté propriétaire (Movie → Merchandise)
            attachedRelatedMovies.forEach(it -> it.getRelatedMerchandise().add(merchandise));
        }
        
        if (merchandise.getRelatedShows() != null &&
            !merchandise.getRelatedShows().isEmpty()) {

            List<TVShow> attachedRelatedShows = merchandise.getRelatedShows().stream()
            .map(item -> relatedShowsRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("TVShow not found with id " + item.getId())))
            .toList();

            merchandise.setRelatedShows(attachedRelatedShows);

            // côté propriétaire (TVShow → Merchandise)
            attachedRelatedShows.forEach(it -> it.getRelatedMerchandise().add(merchandise));
        }
        
        if (merchandise.getOwnedByUsers() != null &&
            !merchandise.getOwnedByUsers().isEmpty()) {

            List<UserProfile> attachedOwnedByUsers = merchandise.getOwnedByUsers().stream()
            .map(item -> ownedByUsersRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId())))
            .toList();

            merchandise.setOwnedByUsers(attachedOwnedByUsers);

            // côté propriétaire (UserProfile → Merchandise)
            attachedOwnedByUsers.forEach(it -> it.getOwnedMerchandise().add(merchandise));
        }
        
    // ---------- ManyToOne ----------
        if (merchandise.getArtist() != null &&
            merchandise.getArtist().getId() != null) {

            Artist existingArtist = artistRepository.findById(
                merchandise.getArtist().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            merchandise.setArtist(existingArtist);
        }
        
        if (merchandise.getProductType() != null &&
            merchandise.getProductType().getId() != null) {

            MerchandiseType existingProductType = productTypeRepository.findById(
                merchandise.getProductType().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseType not found"));

            merchandise.setProductType(existingProductType);
        }
        
        if (merchandise.getSupplier() != null &&
            merchandise.getSupplier().getId() != null) {

            MerchandiseSupplier existingSupplier = supplierRepository.findById(
                merchandise.getSupplier().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseSupplier not found"));

            merchandise.setSupplier(existingSupplier);
        }
        
        if (merchandise.getCollection() != null &&
            merchandise.getCollection().getId() != null) {

            MerchandiseCollection existingCollection = collectionRepository.findById(
                merchandise.getCollection().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseCollection not found"));

            merchandise.setCollection(existingCollection);
        }
        
        if (merchandise.getCategory() != null &&
            merchandise.getCategory().getId() != null) {

            MerchandiseCategory existingCategory = categoryRepository.findById(
                merchandise.getCategory().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseCategory not found"));

            merchandise.setCategory(existingCategory);
        }
        
    // ---------- OneToOne ----------
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

    // ---------- Relations ManyToOne ----------
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
        
        if (merchandiseRequest.getCollection() != null &&
            merchandiseRequest.getCollection().getId() != null) {

            MerchandiseCollection existingCollection = collectionRepository.findById(
                merchandiseRequest.getCollection().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseCollection not found"));

            existing.setCollection(existingCollection);
        } else {
            existing.setCollection(null);
        }
        
        if (merchandiseRequest.getCategory() != null &&
            merchandiseRequest.getCategory().getId() != null) {

            MerchandiseCategory existingCategory = categoryRepository.findById(
                merchandiseRequest.getCategory().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseCategory not found"));

            existing.setCategory(existingCategory);
        } else {
            existing.setCategory(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (merchandiseRequest.getRelatedMovies() != null) {
            existing.getRelatedMovies().clear();

            List<Movie> relatedMoviesList = merchandiseRequest.getRelatedMovies().stream()
                .map(item -> relatedMoviesRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Movie not found")))
                .collect(Collectors.toList());

            existing.getRelatedMovies().addAll(relatedMoviesList);

            // Mettre à jour le côté inverse
            relatedMoviesList.forEach(it -> {
                if (!it.getRelatedMerchandise().contains(existing)) {
                    it.getRelatedMerchandise().add(existing);
                }
            });
        }
        
        if (merchandiseRequest.getRelatedShows() != null) {
            existing.getRelatedShows().clear();

            List<TVShow> relatedShowsList = merchandiseRequest.getRelatedShows().stream()
                .map(item -> relatedShowsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("TVShow not found")))
                .collect(Collectors.toList());

            existing.getRelatedShows().addAll(relatedShowsList);

            // Mettre à jour le côté inverse
            relatedShowsList.forEach(it -> {
                if (!it.getRelatedMerchandise().contains(existing)) {
                    it.getRelatedMerchandise().add(existing);
                }
            });
        }
        
        if (merchandiseRequest.getOwnedByUsers() != null) {
            existing.getOwnedByUsers().clear();

            List<UserProfile> ownedByUsersList = merchandiseRequest.getOwnedByUsers().stream()
                .map(item -> ownedByUsersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getOwnedByUsers().addAll(ownedByUsersList);

            // Mettre à jour le côté inverse
            ownedByUsersList.forEach(it -> {
                if (!it.getOwnedMerchandise().contains(existing)) {
                    it.getOwnedMerchandise().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getReviews().clear();

        if (merchandiseRequest.getReviews() != null) {
            for (var item : merchandiseRequest.getReviews()) {
                MerchandiseReview existingItem;
                if (item.getId() != null) {
                    existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMerchandise(existing);
                existing.getReviews().add(existingItem);
            }
        }
        
        existing.getSales().clear();

        if (merchandiseRequest.getSales() != null) {
            for (var item : merchandiseRequest.getSales()) {
                MerchandiseSale existingItem;
                if (item.getId() != null) {
                    existingItem = salesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseSale not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMerchandiseItem(existing);
                existing.getSales().add(existingItem);
            }
        }
        
        existing.getShipments().clear();

        if (merchandiseRequest.getShipments() != null) {
            for (var item : merchandiseRequest.getShipments()) {
                MerchandiseShipping existingItem;
                if (item.getId() != null) {
                    existingItem = shipmentsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMerchandiseItem(existing);
                existing.getShipments().add(existingItem);
            }
        }
        
        existing.getOrderItems().clear();

        if (merchandiseRequest.getOrderItems() != null) {
            for (var item : merchandiseRequest.getOrderItems()) {
                MerchandiseOrderItem existingItem;
                if (item.getId() != null) {
                    existingItem = orderItemsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseOrderItem not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMerchandiseItem(existing);
                existing.getOrderItems().add(existingItem);
            }
        }
        
        existing.getStockHistory().clear();

        if (merchandiseRequest.getStockHistory() != null) {
            for (var item : merchandiseRequest.getStockHistory()) {
                MerchandiseStockHistory existingItem;
                if (item.getId() != null) {
                    existingItem = stockHistoryRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("MerchandiseStockHistory not found"));
                } else {
                existingItem = item;
                }

                existingItem.setMerchandiseItem(existing);
                existing.getStockHistory().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
            if (merchandiseRequest.getInventory() != null &&
            merchandiseRequest.getInventory().getId() != null) {

            MerchandiseInventory inventory = inventoryRepository.findById(
                merchandiseRequest.getInventory().getId()
            ).orElseThrow(() -> new RuntimeException("MerchandiseInventory not found"));

            existing.setInventory(inventory);

            
            inventory.setMerchandiseItem(existing);
            
        }
        

    return merchandiseRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Merchandise> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Merchandise entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getReviews() != null) {
            for (var child : entity.getReviews()) {
                
                child.setMerchandise(null); // retirer la référence inverse
                
            }
            entity.getReviews().clear();
        }
        
        if (entity.getSales() != null) {
            for (var child : entity.getSales()) {
                
                child.setMerchandiseItem(null); // retirer la référence inverse
                
            }
            entity.getSales().clear();
        }
        
        if (entity.getShipments() != null) {
            for (var child : entity.getShipments()) {
                
                child.setMerchandiseItem(null); // retirer la référence inverse
                
            }
            entity.getShipments().clear();
        }
        
        if (entity.getOrderItems() != null) {
            for (var child : entity.getOrderItems()) {
                
                child.setMerchandiseItem(null); // retirer la référence inverse
                
            }
            entity.getOrderItems().clear();
        }
        
        if (entity.getStockHistory() != null) {
            for (var child : entity.getStockHistory()) {
                
                child.setMerchandiseItem(null); // retirer la référence inverse
                
            }
            entity.getStockHistory().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getRelatedMovies() != null) {
            for (Movie item : new ArrayList<>(entity.getRelatedMovies())) {
                
                item.getRelatedMerchandise().remove(entity); // retire côté inverse
                
            }
            entity.getRelatedMovies().clear(); // puis vide côté courant
        }
        
        if (entity.getRelatedShows() != null) {
            for (TVShow item : new ArrayList<>(entity.getRelatedShows())) {
                
                item.getRelatedMerchandise().remove(entity); // retire côté inverse
                
            }
            entity.getRelatedShows().clear(); // puis vide côté courant
        }
        
        if (entity.getOwnedByUsers() != null) {
            for (UserProfile item : new ArrayList<>(entity.getOwnedByUsers())) {
                
                item.getOwnedMerchandise().remove(entity); // retire côté inverse
                
            }
            entity.getOwnedByUsers().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
        if (entity.getInventory() != null) {
            entity.getInventory().setMerchandiseItem(null);
            entity.setInventory(null);
        }
        
    // --- Dissocier ManyToOne ---
        if (entity.getArtist() != null) {
            entity.setArtist(null);
        }
        
        if (entity.getProductType() != null) {
            entity.setProductType(null);
        }
        
        if (entity.getSupplier() != null) {
            entity.setSupplier(null);
        }
        
        if (entity.getCollection() != null) {
            entity.setCollection(null);
        }
        
        if (entity.getCategory() != null) {
            entity.setCategory(null);
        }
        
        repository.delete(entity);
        return true;
    }
}
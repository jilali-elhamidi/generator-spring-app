package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderItemRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MerchandiseOrderService extends BaseService<MerchandiseOrder> {

    protected final MerchandiseOrderRepository merchandiseorderRepository;
    private final UserProfileRepository userRepository;
    private final MerchandiseOrderItemRepository itemsRepository;
    private final MerchandiseShippingRepository shippingDetailsRepository;

    public MerchandiseOrderService(MerchandiseOrderRepository repository,UserProfileRepository userRepository,MerchandiseOrderItemRepository itemsRepository,MerchandiseShippingRepository shippingDetailsRepository)
    {
        super(repository);
        this.merchandiseorderRepository = repository;
        this.userRepository = userRepository;
        this.itemsRepository = itemsRepository;
        this.shippingDetailsRepository = shippingDetailsRepository;
    }

    @Override
    public MerchandiseOrder save(MerchandiseOrder merchandiseorder) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (merchandiseorder.getItems() != null) {
            List<MerchandiseOrderItem> managedItems = new ArrayList<>();
            for (MerchandiseOrderItem item : merchandiseorder.getItems()) {
            if (item.getId() != null) {
            MerchandiseOrderItem existingItem = itemsRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("MerchandiseOrderItem not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setOrder(merchandiseorder);
            managedItems.add(existingItem);
            } else {
            item.setOrder(merchandiseorder);
            managedItems.add(item);
            }
            }
            merchandiseorder.setItems(managedItems);
            }
        
    

    

    if (merchandiseorder.getUser() != null
        && merchandiseorder.getUser().getId() != null) {
        UserProfile existingUser = userRepository.findById(
        merchandiseorder.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));
        merchandiseorder.setUser(existingUser);
        }
    
    
    
        if (merchandiseorder.getShippingDetails() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            merchandiseorder.setShippingDetails(
            shippingDetailsRepository.findById(merchandiseorder.getShippingDetails().getId())
            .orElseThrow(() -> new RuntimeException("shippingDetails not found"))
            );
        
        merchandiseorder.getShippingDetails().setOrder(merchandiseorder);
        }

    

    

    


        return merchandiseorderRepository.save(merchandiseorder);
    }


    public MerchandiseOrder update(Long id, MerchandiseOrder merchandiseorderRequest) {
        MerchandiseOrder existing = merchandiseorderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseOrder not found"));

    // Copier les champs simples
        existing.setOrderDate(merchandiseorderRequest.getOrderDate());
        existing.setStatus(merchandiseorderRequest.getStatus());

// Relations ManyToOne : mise à jour conditionnelle
        if (merchandiseorderRequest.getUser() != null &&
        merchandiseorderRequest.getUser().getId() != null) {

        UserProfile existingUser = userRepository.findById(
        merchandiseorderRequest.getUser().getId()
        ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

        existing.setUser(existingUser);
        } else {
        existing.setUser(null);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getItems().clear();

        if (merchandiseorderRequest.getItems() != null) {
        for (var item : merchandiseorderRequest.getItems()) {
        MerchandiseOrderItem existingItem;
        if (item.getId() != null) {
        existingItem = itemsRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("MerchandiseOrderItem not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setOrder(existing);

        // Ajouter directement dans la collection existante
        existing.getItems().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    

    

    

        if (merchandiseorderRequest.getShippingDetails() != null
        && merchandiseorderRequest.getShippingDetails().getId() != null) {

        MerchandiseShipping shippingDetails = shippingDetailsRepository.findById(
        merchandiseorderRequest.getShippingDetails().getId()
        ).orElseThrow(() -> new RuntimeException("MerchandiseShipping not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setShippingDetails(shippingDetails);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            shippingDetails.setOrder(existing);
        
        }

    


        return merchandiseorderRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<MerchandiseOrder> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

MerchandiseOrder entity = entityOpt.get();

// --- Dissocier OneToMany ---

    

    
        if (entity.getItems() != null) {
        for (var child : entity.getItems()) {
        
            child.setOrder(null); // retirer la référence inverse
        
        }
        entity.getItems().clear();
        }
    

    


// --- Dissocier ManyToMany ---

    

    

    


// --- Dissocier OneToOne ---

    

    

    
        if (entity.getShippingDetails() != null) {
        // Dissocier côté inverse automatiquement
        entity.getShippingDetails().setOrder(null);
        // Dissocier côté direct
        entity.setShippingDetails(null);
        }
    


// --- Dissocier ManyToOne ---

    
        if (entity.getUser() != null) {
        entity.setUser(null);
        }
    

    

    


repository.delete(entity);
return true;
}
}
package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrder;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseOrderRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.MerchandiseOrderItem;
import com.example.modules.entertainment_ecosystem.model.MerchandiseShipping;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseShippingRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MerchandiseOrderService extends BaseService<MerchandiseOrder> {

    protected final MerchandiseOrderRepository merchandiseorderRepository;
    private final UserProfileRepository userRepository;
    private final MerchandiseShippingRepository shippingDetailsRepository;

    public MerchandiseOrderService(MerchandiseOrderRepository repository,UserProfileRepository userRepository,MerchandiseShippingRepository shippingDetailsRepository)
    {
        super(repository);
        this.merchandiseorderRepository = repository;
        this.userRepository = userRepository;
            this.shippingDetailsRepository = shippingDetailsRepository;
    }

    @Override
    public MerchandiseOrder save(MerchandiseOrder merchandiseorder) {

        if (merchandiseorder.getUser() != null && merchandiseorder.getUser().getId() != null) {
        UserProfile user = userRepository.findById(merchandiseorder.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        merchandiseorder.setUser(user);
        }

        if (merchandiseorder.getItems() != null) {
            for (MerchandiseOrderItem item : merchandiseorder.getItems()) {
            item.setOrder(merchandiseorder);
            }
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

        if (merchandiseorderRequest.getUser() != null && merchandiseorderRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(merchandiseorderRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getItems().clear();
        if (merchandiseorderRequest.getItems() != null) {
            for (var item : merchandiseorderRequest.getItems()) {
            item.setOrder(existing);
            existing.getItems().add(item);
            }
        }

    

    

    

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
}
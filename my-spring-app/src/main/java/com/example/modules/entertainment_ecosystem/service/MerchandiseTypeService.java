package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseType;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseTypeRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MerchandiseTypeService extends BaseService<MerchandiseType> {

    protected final MerchandiseTypeRepository merchandisetypeRepository;

    public MerchandiseTypeService(MerchandiseTypeRepository repository)
    {
        super(repository);
        this.merchandisetypeRepository = repository;
    }

    @Override
    public MerchandiseType save(MerchandiseType merchandisetype) {

        if (merchandisetype.getItems() != null) {
            for (Merchandise item : merchandisetype.getItems()) {
            item.setProductType(merchandisetype);
            }
        }

        return merchandisetypeRepository.save(merchandisetype);
    }


    public MerchandiseType update(Long id, MerchandiseType merchandisetypeRequest) {
        MerchandiseType existing = merchandisetypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseType not found"));

    // Copier les champs simples
        existing.setName(merchandisetypeRequest.getName());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        existing.getItems().clear();
        if (merchandisetypeRequest.getItems() != null) {
            for (var item : merchandisetypeRequest.getItems()) {
            item.setProductType(existing);
            existing.getItems().add(item);
            }
        }

    


        return merchandisetypeRepository.save(existing);
    }
}
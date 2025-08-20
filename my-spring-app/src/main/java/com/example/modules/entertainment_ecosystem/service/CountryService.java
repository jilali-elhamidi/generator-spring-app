package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Country;
import com.example.modules.entertainment_ecosystem.repository.CountryRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class CountryService extends BaseService<Country> {

    protected final CountryRepository countryRepository;
    private final UserProfileRepository userProfilesRepository;

    public CountryService(CountryRepository repository,UserProfileRepository userProfilesRepository)
    {
        super(repository);
        this.countryRepository = repository;
        this.userProfilesRepository = userProfilesRepository;
    }

    @Override
    public Country save(Country country) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (country.getUserProfiles() != null) {
            List<UserProfile> managedUserProfiles = new ArrayList<>();
            for (UserProfile item : country.getUserProfiles()) {
            if (item.getId() != null) {
            UserProfile existingItem = userProfilesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserProfile not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setCountry(country);
            managedUserProfiles.add(existingItem);
            } else {
            item.setCountry(country);
            managedUserProfiles.add(item);
            }
            }
            country.setUserProfiles(managedUserProfiles);
            }
        
    


    

    

        return countryRepository.save(country);
    }


    public Country update(Long id, Country countryRequest) {
        Country existing = countryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Country not found"));

    // Copier les champs simples
        existing.setName(countryRequest.getName());
        existing.setCode(countryRequest.getCode());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getUserProfiles().clear();

        if (countryRequest.getUserProfiles() != null) {
        for (var item : countryRequest.getUserProfiles()) {
        UserProfile existingItem;
        if (item.getId() != null) {
        existingItem = userProfilesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setCountry(existing);

        // Ajouter directement dans la collection existante
        existing.getUserProfiles().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return countryRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Country> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Country entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getUserProfiles() != null) {
        for (var child : entity.getUserProfiles()) {
        
            child.setCountry(null); // retirer la référence inverse
        
        }
        entity.getUserProfiles().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}
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

    public CountryService(CountryRepository repository, UserProfileRepository userProfilesRepository)
    {
        super(repository);
        this.countryRepository = repository;
        this.userProfilesRepository = userProfilesRepository;
    }

    @Override
    public Country save(Country country) {
    // ---------- OneToMany ----------
        if (country.getUserProfiles() != null) {
            List<UserProfile> managedUserProfiles = new ArrayList<>();
            for (UserProfile item : country.getUserProfiles()) {
                if (item.getId() != null) {
                    UserProfile existingItem = userProfilesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found"));

                     existingItem.setCountry(country);
                     managedUserProfiles.add(existingItem);
                } else {
                    item.setCountry(country);
                    managedUserProfiles.add(item);
                }
            }
            country.setUserProfiles(managedUserProfiles);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return countryRepository.save(country);
}


    public Country update(Long id, Country countryRequest) {
        Country existing = countryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Country not found"));

    // Copier les champs simples
        existing.setName(countryRequest.getName());
        existing.setCode(countryRequest.getCode());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
        existing.getUserProfiles().clear();

        if (countryRequest.getUserProfiles() != null) {
            for (var item : countryRequest.getUserProfiles()) {
                UserProfile existingItem;
                if (item.getId() != null) {
                    existingItem = userProfilesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found"));
                } else {
                existingItem = item;
                }

                existingItem.setCountry(existing);
                existing.getUserProfiles().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
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
                // retirer la référence inverse
                child.setCountry(null);
            }
            entity.getUserProfiles().clear();
        }
        
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Country> saveAll(List<Country> countryList) {

        return countryRepository.saveAll(countryList);
    }

}
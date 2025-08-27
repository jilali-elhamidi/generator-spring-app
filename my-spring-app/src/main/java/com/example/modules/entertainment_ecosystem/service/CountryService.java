package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Country;
import com.example.modules.entertainment_ecosystem.repository.CountryRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class CountryService extends BaseService<Country> {

    protected final CountryRepository countryRepository;
    
    protected final UserProfileRepository userProfilesRepository;
    

    public CountryService(CountryRepository repository, UserProfileRepository userProfilesRepository)
    {
        super(repository);
        this.countryRepository = repository;
        
        this.userProfilesRepository = userProfilesRepository;
        
    }

    @Transactional
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

    @Transactional
    @Override
    public Country update(Long id, Country countryRequest) {
        Country existing = countryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Country not found"));

    // Copier les champs simples
        existing.setName(countryRequest.getName());
        existing.setCode(countryRequest.getCode());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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

    // Pagination simple
    public Page<Country> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Country> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Country.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Country> saveAll(List<Country> countryList) {
        return super.saveAll(countryList);
    }

}
package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseReviewRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MerchandiseReviewService extends BaseService<MerchandiseReview> {

    protected final MerchandiseReviewRepository merchandisereviewRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final MerchandiseRepository merchandiseRepository;
    

    public MerchandiseReviewService(MerchandiseReviewRepository repository, UserProfileRepository userRepository, MerchandiseRepository merchandiseRepository)
    {
        super(repository);
        this.merchandisereviewRepository = repository;
        
        this.userRepository = userRepository;
        
        this.merchandiseRepository = merchandiseRepository;
        
    }

    @Transactional
    @Override
    public MerchandiseReview save(MerchandiseReview merchandisereview) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (merchandisereview.getUser() != null) {
            if (merchandisereview.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    merchandisereview.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + merchandisereview.getUser().getId()));
                merchandisereview.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(merchandisereview.getUser());
                merchandisereview.setUser(newUser);
            }
        }
        
        if (merchandisereview.getMerchandise() != null) {
            if (merchandisereview.getMerchandise().getId() != null) {
                Merchandise existingMerchandise = merchandiseRepository.findById(
                    merchandisereview.getMerchandise().getId()
                ).orElseThrow(() -> new RuntimeException("Merchandise not found with id "
                    + merchandisereview.getMerchandise().getId()));
                merchandisereview.setMerchandise(existingMerchandise);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Merchandise newMerchandise = merchandiseRepository.save(merchandisereview.getMerchandise());
                merchandisereview.setMerchandise(newMerchandise);
            }
        }
        
    // ---------- OneToOne ----------
    return merchandisereviewRepository.save(merchandisereview);
}

    @Transactional
    @Override
    public MerchandiseReview update(Long id, MerchandiseReview merchandisereviewRequest) {
        MerchandiseReview existing = merchandisereviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));

    // Copier les champs simples
        existing.setRating(merchandisereviewRequest.getRating());
        existing.setComment(merchandisereviewRequest.getComment());
        existing.setReviewDate(merchandisereviewRequest.getReviewDate());

    // ---------- Relations ManyToOne ----------
        if (merchandisereviewRequest.getUser() != null &&
            merchandisereviewRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                merchandisereviewRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (merchandisereviewRequest.getMerchandise() != null &&
            merchandisereviewRequest.getMerchandise().getId() != null) {

            Merchandise existingMerchandise = merchandiseRepository.findById(
                merchandisereviewRequest.getMerchandise().getId()
            ).orElseThrow(() -> new RuntimeException("Merchandise not found"));

            existing.setMerchandise(existingMerchandise);
        } else {
            existing.setMerchandise(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return merchandisereviewRepository.save(existing);
}

    // Pagination simple
    public Page<MerchandiseReview> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MerchandiseReview> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MerchandiseReview.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MerchandiseReview> saveAll(List<MerchandiseReview> merchandisereviewList) {
        return super.saveAll(merchandisereviewList);
    }

}
package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MerchandiseReview;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseReviewRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Merchandise;
import com.example.modules.entertainment_ecosystem.repository.MerchandiseRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MerchandiseReviewService extends BaseService<MerchandiseReview> {

    protected final MerchandiseReviewRepository merchandisereviewRepository;
    private final UserProfileRepository userRepository;
    private final MerchandiseRepository merchandiseRepository;

    public MerchandiseReviewService(MerchandiseReviewRepository repository,UserProfileRepository userRepository,MerchandiseRepository merchandiseRepository)
    {
        super(repository);
        this.merchandisereviewRepository = repository;
        this.userRepository = userRepository;
        this.merchandiseRepository = merchandiseRepository;
    }

    @Override
    public MerchandiseReview save(MerchandiseReview merchandisereview) {

        if (merchandisereview.getUser() != null && merchandisereview.getUser().getId() != null) {
        UserProfile user = userRepository.findById(merchandisereview.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        merchandisereview.setUser(user);
        }

        if (merchandisereview.getMerchandise() != null && merchandisereview.getMerchandise().getId() != null) {
        Merchandise merchandise = merchandiseRepository.findById(merchandisereview.getMerchandise().getId())
                .orElseThrow(() -> new RuntimeException("Merchandise not found"));
        merchandisereview.setMerchandise(merchandise);
        }

        return merchandisereviewRepository.save(merchandisereview);
    }


    public MerchandiseReview update(Long id, MerchandiseReview merchandisereviewRequest) {
        MerchandiseReview existing = merchandisereviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MerchandiseReview not found"));

    // Copier les champs simples
        existing.setRating(merchandisereviewRequest.getRating());
        existing.setComment(merchandisereviewRequest.getComment());
        existing.setReviewDate(merchandisereviewRequest.getReviewDate());

// Relations ManyToOne : mise à jour conditionnelle

        if (merchandisereviewRequest.getUser() != null && merchandisereviewRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(merchandisereviewRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (merchandisereviewRequest.getMerchandise() != null && merchandisereviewRequest.getMerchandise().getId() != null) {
        Merchandise merchandise = merchandiseRepository.findById(merchandisereviewRequest.getMerchandise().getId())
                .orElseThrow(() -> new RuntimeException("Merchandise not found"));
        existing.setMerchandise(merchandise);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    


        return merchandisereviewRepository.save(existing);
    }
}
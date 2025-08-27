package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.DigitalPurchase;
import com.example.modules.entertainment_ecosystem.repository.DigitalPurchaseRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.Movie;
import com.example.modules.entertainment_ecosystem.repository.MovieRepository;

import com.example.modules.entertainment_ecosystem.model.MusicTrack;
import com.example.modules.entertainment_ecosystem.repository.MusicTrackRepository;

import com.example.modules.entertainment_ecosystem.model.VideoGame;
import com.example.modules.entertainment_ecosystem.repository.VideoGameRepository;

import com.example.modules.entertainment_ecosystem.model.Transaction;
import com.example.modules.entertainment_ecosystem.repository.TransactionRepository;

import com.example.modules.entertainment_ecosystem.model.GameExpansionPack;
import com.example.modules.entertainment_ecosystem.repository.GameExpansionPackRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DigitalPurchaseService extends BaseService<DigitalPurchase> {

    protected final DigitalPurchaseRepository digitalpurchaseRepository;
    
    protected final UserProfileRepository userRepository;
    
    protected final MovieRepository movieRepository;
    
    protected final MusicTrackRepository musicTrackRepository;
    
    protected final VideoGameRepository videoGameRepository;
    
    protected final TransactionRepository transactionRepository;
    
    protected final GameExpansionPackRepository expansionPackRepository;
    

    public DigitalPurchaseService(DigitalPurchaseRepository repository, UserProfileRepository userRepository, MovieRepository movieRepository, MusicTrackRepository musicTrackRepository, VideoGameRepository videoGameRepository, TransactionRepository transactionRepository, GameExpansionPackRepository expansionPackRepository)
    {
        super(repository);
        this.digitalpurchaseRepository = repository;
        
        this.userRepository = userRepository;
        
        this.movieRepository = movieRepository;
        
        this.musicTrackRepository = musicTrackRepository;
        
        this.videoGameRepository = videoGameRepository;
        
        this.transactionRepository = transactionRepository;
        
        this.expansionPackRepository = expansionPackRepository;
        
    }

    @Transactional
    @Override
    public DigitalPurchase save(DigitalPurchase digitalpurchase) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (digitalpurchase.getUser() != null) {
            if (digitalpurchase.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    digitalpurchase.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + digitalpurchase.getUser().getId()));
                digitalpurchase.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(digitalpurchase.getUser());
                digitalpurchase.setUser(newUser);
            }
        }
        
        if (digitalpurchase.getMovie() != null) {
            if (digitalpurchase.getMovie().getId() != null) {
                Movie existingMovie = movieRepository.findById(
                    digitalpurchase.getMovie().getId()
                ).orElseThrow(() -> new RuntimeException("Movie not found with id "
                    + digitalpurchase.getMovie().getId()));
                digitalpurchase.setMovie(existingMovie);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Movie newMovie = movieRepository.save(digitalpurchase.getMovie());
                digitalpurchase.setMovie(newMovie);
            }
        }
        
        if (digitalpurchase.getMusicTrack() != null) {
            if (digitalpurchase.getMusicTrack().getId() != null) {
                MusicTrack existingMusicTrack = musicTrackRepository.findById(
                    digitalpurchase.getMusicTrack().getId()
                ).orElseThrow(() -> new RuntimeException("MusicTrack not found with id "
                    + digitalpurchase.getMusicTrack().getId()));
                digitalpurchase.setMusicTrack(existingMusicTrack);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MusicTrack newMusicTrack = musicTrackRepository.save(digitalpurchase.getMusicTrack());
                digitalpurchase.setMusicTrack(newMusicTrack);
            }
        }
        
        if (digitalpurchase.getVideoGame() != null) {
            if (digitalpurchase.getVideoGame().getId() != null) {
                VideoGame existingVideoGame = videoGameRepository.findById(
                    digitalpurchase.getVideoGame().getId()
                ).orElseThrow(() -> new RuntimeException("VideoGame not found with id "
                    + digitalpurchase.getVideoGame().getId()));
                digitalpurchase.setVideoGame(existingVideoGame);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                VideoGame newVideoGame = videoGameRepository.save(digitalpurchase.getVideoGame());
                digitalpurchase.setVideoGame(newVideoGame);
            }
        }
        
        if (digitalpurchase.getExpansionPack() != null) {
            if (digitalpurchase.getExpansionPack().getId() != null) {
                GameExpansionPack existingExpansionPack = expansionPackRepository.findById(
                    digitalpurchase.getExpansionPack().getId()
                ).orElseThrow(() -> new RuntimeException("GameExpansionPack not found with id "
                    + digitalpurchase.getExpansionPack().getId()));
                digitalpurchase.setExpansionPack(existingExpansionPack);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                GameExpansionPack newExpansionPack = expansionPackRepository.save(digitalpurchase.getExpansionPack());
                digitalpurchase.setExpansionPack(newExpansionPack);
            }
        }
        
    // ---------- OneToOne ----------
        if (digitalpurchase.getTransaction() != null) {
            if (digitalpurchase.getTransaction().getId() != null) {
                Transaction existingTransaction = transactionRepository.findById(digitalpurchase.getTransaction().getId())
                    .orElseThrow(() -> new RuntimeException("Transaction not found with id "
                        + digitalpurchase.getTransaction().getId()));
                digitalpurchase.setTransaction(existingTransaction);
            } else {
                // Nouvel objet → sauvegarde d'abord
                Transaction newTransaction = transactionRepository.save(digitalpurchase.getTransaction());
                digitalpurchase.setTransaction(newTransaction);
            }

            digitalpurchase.getTransaction().setDigitalPurchase(digitalpurchase);
        }
        
    return digitalpurchaseRepository.save(digitalpurchase);
}

    @Transactional
    @Override
    public DigitalPurchase update(Long id, DigitalPurchase digitalpurchaseRequest) {
        DigitalPurchase existing = digitalpurchaseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));

    // Copier les champs simples
        existing.setPurchaseDate(digitalpurchaseRequest.getPurchaseDate());
        existing.setPrice(digitalpurchaseRequest.getPrice());

    // ---------- Relations ManyToOne ----------
        if (digitalpurchaseRequest.getUser() != null &&
            digitalpurchaseRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                digitalpurchaseRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (digitalpurchaseRequest.getMovie() != null &&
            digitalpurchaseRequest.getMovie().getId() != null) {

            Movie existingMovie = movieRepository.findById(
                digitalpurchaseRequest.getMovie().getId()
            ).orElseThrow(() -> new RuntimeException("Movie not found"));

            existing.setMovie(existingMovie);
        } else {
            existing.setMovie(null);
        }
        
        if (digitalpurchaseRequest.getMusicTrack() != null &&
            digitalpurchaseRequest.getMusicTrack().getId() != null) {

            MusicTrack existingMusicTrack = musicTrackRepository.findById(
                digitalpurchaseRequest.getMusicTrack().getId()
            ).orElseThrow(() -> new RuntimeException("MusicTrack not found"));

            existing.setMusicTrack(existingMusicTrack);
        } else {
            existing.setMusicTrack(null);
        }
        
        if (digitalpurchaseRequest.getVideoGame() != null &&
            digitalpurchaseRequest.getVideoGame().getId() != null) {

            VideoGame existingVideoGame = videoGameRepository.findById(
                digitalpurchaseRequest.getVideoGame().getId()
            ).orElseThrow(() -> new RuntimeException("VideoGame not found"));

            existing.setVideoGame(existingVideoGame);
        } else {
            existing.setVideoGame(null);
        }
        
        if (digitalpurchaseRequest.getExpansionPack() != null &&
            digitalpurchaseRequest.getExpansionPack().getId() != null) {

            GameExpansionPack existingExpansionPack = expansionPackRepository.findById(
                digitalpurchaseRequest.getExpansionPack().getId()
            ).orElseThrow(() -> new RuntimeException("GameExpansionPack not found"));

            existing.setExpansionPack(existingExpansionPack);
        } else {
            existing.setExpansionPack(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
        if (digitalpurchaseRequest.getTransaction() != null &&digitalpurchaseRequest.getTransaction().getId() != null) {

        Transaction transaction = transactionRepository.findById(digitalpurchaseRequest.getTransaction().getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        existing.setTransaction(transaction);
        transaction.setDigitalPurchase(existing);
        }
    
    return digitalpurchaseRepository.save(existing);
}

    // Pagination simple
    public Page<DigitalPurchase> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<DigitalPurchase> search(Map<String, String> filters, Pageable pageable) {
        return super.search(DigitalPurchase.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<DigitalPurchase> saveAll(List<DigitalPurchase> digitalpurchaseList) {
        return super.saveAll(digitalpurchaseList);
    }

}
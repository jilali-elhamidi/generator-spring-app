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

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class DigitalPurchaseService extends BaseService<DigitalPurchase> {

    protected final DigitalPurchaseRepository digitalpurchaseRepository;
    private final UserProfileRepository userRepository;
    private final MovieRepository movieRepository;
    private final MusicTrackRepository musicTrackRepository;
    private final VideoGameRepository videoGameRepository;
    private final TransactionRepository transactionRepository;

    public DigitalPurchaseService(DigitalPurchaseRepository repository,UserProfileRepository userRepository,MovieRepository movieRepository,MusicTrackRepository musicTrackRepository,VideoGameRepository videoGameRepository,TransactionRepository transactionRepository)
    {
        super(repository);
        this.digitalpurchaseRepository = repository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.musicTrackRepository = musicTrackRepository;
        this.videoGameRepository = videoGameRepository;
            this.transactionRepository = transactionRepository;
    }

    @Override
    public DigitalPurchase save(DigitalPurchase digitalpurchase) {

        if (digitalpurchase.getUser() != null && digitalpurchase.getUser().getId() != null) {
        UserProfile user = userRepository.findById(digitalpurchase.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        digitalpurchase.setUser(user);
        }

        if (digitalpurchase.getMovie() != null && digitalpurchase.getMovie().getId() != null) {
        Movie movie = movieRepository.findById(digitalpurchase.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        digitalpurchase.setMovie(movie);
        }

        if (digitalpurchase.getMusicTrack() != null && digitalpurchase.getMusicTrack().getId() != null) {
        MusicTrack musicTrack = musicTrackRepository.findById(digitalpurchase.getMusicTrack().getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        digitalpurchase.setMusicTrack(musicTrack);
        }

        if (digitalpurchase.getVideoGame() != null && digitalpurchase.getVideoGame().getId() != null) {
        VideoGame videoGame = videoGameRepository.findById(digitalpurchase.getVideoGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        digitalpurchase.setVideoGame(videoGame);
        }
        if (digitalpurchase.getTransaction() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            digitalpurchase.setTransaction(
            transactionRepository.findById(digitalpurchase.getTransaction().getId())
            .orElseThrow(() -> new RuntimeException("transaction not found"))
            );
        
        digitalpurchase.getTransaction().setDigitalPurchase(digitalpurchase);
        }

        return digitalpurchaseRepository.save(digitalpurchase);
    }


    public DigitalPurchase update(Long id, DigitalPurchase digitalpurchaseRequest) {
        DigitalPurchase existing = digitalpurchaseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("DigitalPurchase not found"));

    // Copier les champs simples
        existing.setPurchaseDate(digitalpurchaseRequest.getPurchaseDate());
        existing.setPrice(digitalpurchaseRequest.getPrice());

// Relations ManyToOne : mise à jour conditionnelle

        if (digitalpurchaseRequest.getUser() != null && digitalpurchaseRequest.getUser().getId() != null) {
        UserProfile user = userRepository.findById(digitalpurchaseRequest.getUser().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setUser(user);
        }

        if (digitalpurchaseRequest.getMovie() != null && digitalpurchaseRequest.getMovie().getId() != null) {
        Movie movie = movieRepository.findById(digitalpurchaseRequest.getMovie().getId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        existing.setMovie(movie);
        }

        if (digitalpurchaseRequest.getMusicTrack() != null && digitalpurchaseRequest.getMusicTrack().getId() != null) {
        MusicTrack musicTrack = musicTrackRepository.findById(digitalpurchaseRequest.getMusicTrack().getId())
                .orElseThrow(() -> new RuntimeException("MusicTrack not found"));
        existing.setMusicTrack(musicTrack);
        }

        if (digitalpurchaseRequest.getVideoGame() != null && digitalpurchaseRequest.getVideoGame().getId() != null) {
        VideoGame videoGame = videoGameRepository.findById(digitalpurchaseRequest.getVideoGame().getId())
                .orElseThrow(() -> new RuntimeException("VideoGame not found"));
        existing.setVideoGame(videoGame);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    

    

    

        if (digitalpurchaseRequest.getTransaction() != null
        && digitalpurchaseRequest.getTransaction().getId() != null) {

        Transaction transaction = transactionRepository.findById(
        digitalpurchaseRequest.getTransaction().getId()
        ).orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setTransaction(transaction);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            transaction.setDigitalPurchase(existing);
        
        }

    


        return digitalpurchaseRepository.save(existing);
    }
}
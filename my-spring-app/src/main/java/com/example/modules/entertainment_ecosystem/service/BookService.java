package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.repository.PublisherRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class BookService extends BaseService<Book> {

    protected final BookRepository bookRepository;
    private final ArtistRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final GenreRepository genresRepository;

    public BookService(BookRepository repository,ArtistRepository authorRepository,PublisherRepository publisherRepository,GenreRepository genresRepository)
    {
        super(repository);
        this.bookRepository = repository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.genresRepository = genresRepository;
    }

    @Override
    public Book save(Book book) {

        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
        Artist author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        book.setAuthor(author);
        }

        if (book.getPublisher() != null && book.getPublisher().getId() != null) {
        Publisher publisher = publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        book.setPublisher(publisher);
        }

        if (book.getReviews() != null) {
            for (Review item : book.getReviews()) {
            item.setBook(book);
            }
        }

        return bookRepository.save(book);
    }


    public Book update(Long id, Book bookRequest) {
        Book existing = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

    // Copier les champs simples
        existing.setTitle(bookRequest.getTitle());
        existing.setPublishedDate(bookRequest.getPublishedDate());
        existing.setIsbn(bookRequest.getIsbn());
        existing.setSynopsis(bookRequest.getSynopsis());

// Relations ManyToOne : mise à jour conditionnelle

        if (bookRequest.getAuthor() != null && bookRequest.getAuthor().getId() != null) {
        Artist author = authorRepository.findById(bookRequest.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));
        existing.setAuthor(author);
        }

        if (bookRequest.getPublisher() != null && bookRequest.getPublisher().getId() != null) {
        Publisher publisher = publisherRepository.findById(bookRequest.getPublisher().getId())
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        existing.setPublisher(publisher);
        }

// Relations ManyToMany : synchronisation sécurisée

        if (bookRequest.getGenres() != null) {
            existing.getGenres().clear();
            List<Genre> genresList = bookRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());
        existing.getGenres().addAll(genresList);
        }

// Relations OneToMany : synchronisation sécurisée

        existing.getReviews().clear();
        if (bookRequest.getReviews() != null) {
            for (var item : bookRequest.getReviews()) {
            item.setBook(existing);
            existing.getReviews().add(item);
            }
        }

    

    

    

    


        return bookRepository.save(existing);
    }
}
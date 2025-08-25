package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;
import com.example.modules.entertainment_ecosystem.model.Publisher;
import com.example.modules.entertainment_ecosystem.repository.PublisherRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;
import com.example.modules.entertainment_ecosystem.model.Genre;
import com.example.modules.entertainment_ecosystem.repository.GenreRepository;
import com.example.modules.entertainment_ecosystem.model.BookSeries;
import com.example.modules.entertainment_ecosystem.repository.BookSeriesRepository;
import com.example.modules.entertainment_ecosystem.model.BookCharacter;
import com.example.modules.entertainment_ecosystem.repository.BookCharacterRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class BookService extends BaseService<Book> {

    protected final BookRepository bookRepository;
    private final ArtistRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final ReviewRepository reviewsRepository;
    private final GenreRepository genresRepository;
    private final BookSeriesRepository seriesRepository;
    private final BookCharacterRepository charactersRepository;

    public BookService(BookRepository repository, ArtistRepository authorRepository, PublisherRepository publisherRepository, ReviewRepository reviewsRepository, GenreRepository genresRepository, BookSeriesRepository seriesRepository, BookCharacterRepository charactersRepository)
    {
        super(repository);
        this.bookRepository = repository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.reviewsRepository = reviewsRepository;
        this.genresRepository = genresRepository;
        this.seriesRepository = seriesRepository;
        this.charactersRepository = charactersRepository;
    }

    @Override
    public Book save(Book book) {
    // ---------- OneToMany ----------
        if (book.getReviews() != null) {
            List<Review> managedReviews = new ArrayList<>();
            for (Review item : book.getReviews()) {
                if (item.getId() != null) {
                    Review existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Review not found"));

                     existingItem.setBook(book);
                     managedReviews.add(existingItem);
                } else {
                    item.setBook(book);
                    managedReviews.add(item);
                }
            }
            book.setReviews(managedReviews);
        }
    
    // ---------- ManyToMany ----------
        if (book.getGenres() != null &&
            !book.getGenres().isEmpty()) {

            List<Genre> attachedGenres = new ArrayList<>();
            for (Genre item : book.getGenres()) {
                if (item.getId() != null) {
                    Genre existingItem = genresRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Genre not found with id " + item.getId()));
                    attachedGenres.add(existingItem);
                } else {

                    Genre newItem = genresRepository.save(item);
                    attachedGenres.add(newItem);
                }
            }

            book.setGenres(attachedGenres);

            // côté propriétaire (Genre → Book)
            attachedGenres.forEach(it -> it.getBookGenres().add(book));
        }
        
        if (book.getCharacters() != null &&
            !book.getCharacters().isEmpty()) {

            List<BookCharacter> attachedCharacters = new ArrayList<>();
            for (BookCharacter item : book.getCharacters()) {
                if (item.getId() != null) {
                    BookCharacter existingItem = charactersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("BookCharacter not found with id " + item.getId()));
                    attachedCharacters.add(existingItem);
                } else {

                    BookCharacter newItem = charactersRepository.save(item);
                    attachedCharacters.add(newItem);
                }
            }

            book.setCharacters(attachedCharacters);

            // côté propriétaire (BookCharacter → Book)
            attachedCharacters.forEach(it -> it.getBooks().add(book));
        }
        
    // ---------- ManyToOne ----------
        if (book.getAuthor() != null) {
            if (book.getAuthor().getId() != null) {
                Artist existingAuthor = authorRepository.findById(
                    book.getAuthor().getId()
                ).orElseThrow(() -> new RuntimeException("Artist not found with id "
                    + book.getAuthor().getId()));
                book.setAuthor(existingAuthor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Artist newAuthor = authorRepository.save(book.getAuthor());
                book.setAuthor(newAuthor);
            }
        }
        
        if (book.getPublisher() != null) {
            if (book.getPublisher().getId() != null) {
                Publisher existingPublisher = publisherRepository.findById(
                    book.getPublisher().getId()
                ).orElseThrow(() -> new RuntimeException("Publisher not found with id "
                    + book.getPublisher().getId()));
                book.setPublisher(existingPublisher);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Publisher newPublisher = publisherRepository.save(book.getPublisher());
                book.setPublisher(newPublisher);
            }
        }
        
        if (book.getSeries() != null) {
            if (book.getSeries().getId() != null) {
                BookSeries existingSeries = seriesRepository.findById(
                    book.getSeries().getId()
                ).orElseThrow(() -> new RuntimeException("BookSeries not found with id "
                    + book.getSeries().getId()));
                book.setSeries(existingSeries);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                BookSeries newSeries = seriesRepository.save(book.getSeries());
                book.setSeries(newSeries);
            }
        }
        
    // ---------- OneToOne ----------
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

    // ---------- Relations ManyToOne ----------
        if (bookRequest.getAuthor() != null &&
            bookRequest.getAuthor().getId() != null) {

            Artist existingAuthor = authorRepository.findById(
                bookRequest.getAuthor().getId()
            ).orElseThrow(() -> new RuntimeException("Artist not found"));

            existing.setAuthor(existingAuthor);
        } else {
            existing.setAuthor(null);
        }
        
        if (bookRequest.getPublisher() != null &&
            bookRequest.getPublisher().getId() != null) {

            Publisher existingPublisher = publisherRepository.findById(
                bookRequest.getPublisher().getId()
            ).orElseThrow(() -> new RuntimeException("Publisher not found"));

            existing.setPublisher(existingPublisher);
        } else {
            existing.setPublisher(null);
        }
        
        if (bookRequest.getSeries() != null &&
            bookRequest.getSeries().getId() != null) {

            BookSeries existingSeries = seriesRepository.findById(
                bookRequest.getSeries().getId()
            ).orElseThrow(() -> new RuntimeException("BookSeries not found"));

            existing.setSeries(existingSeries);
        } else {
            existing.setSeries(null);
        }
        
    // ---------- Relations ManyToOne ----------
        if (bookRequest.getGenres() != null) {
            existing.getGenres().clear();

            List<Genre> genresList = bookRequest.getGenres().stream()
                .map(item -> genresRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Genre not found")))
                .collect(Collectors.toList());

            existing.getGenres().addAll(genresList);

            // Mettre à jour le côté inverse
            genresList.forEach(it -> {
                if (!it.getBookGenres().contains(existing)) {
                    it.getBookGenres().add(existing);
                }
            });
        }
        
        if (bookRequest.getCharacters() != null) {
            existing.getCharacters().clear();

            List<BookCharacter> charactersList = bookRequest.getCharacters().stream()
                .map(item -> charactersRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("BookCharacter not found")))
                .collect(Collectors.toList());

            existing.getCharacters().addAll(charactersList);

            // Mettre à jour le côté inverse
            charactersList.forEach(it -> {
                if (!it.getBooks().contains(existing)) {
                    it.getBooks().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getReviews().clear();

        if (bookRequest.getReviews() != null) {
            for (var item : bookRequest.getReviews()) {
                Review existingItem;
                if (item.getId() != null) {
                    existingItem = reviewsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Review not found"));
                } else {
                existingItem = item;
                }

                existingItem.setBook(existing);
                existing.getReviews().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return bookRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Book> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Book entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getReviews() != null) {
            for (var child : entity.getReviews()) {
                // retirer la référence inverse
                child.setBook(null);
            }
            entity.getReviews().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getGenres() != null) {
            for (Genre item : new ArrayList<>(entity.getGenres())) {
                
                item.getBookGenres().remove(entity); // retire côté inverse
            }
            entity.getGenres().clear(); // puis vide côté courant
        }
        
        if (entity.getCharacters() != null) {
            for (BookCharacter item : new ArrayList<>(entity.getCharacters())) {
                
                item.getBooks().remove(entity); // retire côté inverse
            }
            entity.getCharacters().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getAuthor() != null) {
            entity.setAuthor(null);
        }
        
        if (entity.getPublisher() != null) {
            entity.setPublisher(null);
        }
        
        if (entity.getSeries() != null) {
            entity.setSeries(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<Book> saveAll(List<Book> bookList) {

        return bookRepository.saveAll(bookList);
    }

}
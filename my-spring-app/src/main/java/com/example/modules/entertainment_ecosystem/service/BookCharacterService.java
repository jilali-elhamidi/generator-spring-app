package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.BookCharacter;
import com.example.modules.entertainment_ecosystem.repository.BookCharacterRepository;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class BookCharacterService extends BaseService<BookCharacter> {

    protected final BookCharacterRepository bookcharacterRepository;
    private final BookRepository booksRepository;

    public BookCharacterService(BookCharacterRepository repository, BookRepository booksRepository)
    {
        super(repository);
        this.bookcharacterRepository = repository;
        this.booksRepository = booksRepository;
    }

    @Override
    public BookCharacter save(BookCharacter bookcharacter) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (bookcharacter.getBooks() != null &&
            !bookcharacter.getBooks().isEmpty()) {

            List<Book> attachedBooks = bookcharacter.getBooks().stream()
            .map(item -> booksRepository.findById(item.getId())
                .orElseThrow(() -> new RuntimeException("Book not found with id " + item.getId())))
            .toList();

            bookcharacter.setBooks(attachedBooks);

            // côté propriétaire (Book → BookCharacter)
            attachedBooks.forEach(it -> it.getCharacters().add(bookcharacter));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------

    return bookcharacterRepository.save(bookcharacter);
}


    public BookCharacter update(Long id, BookCharacter bookcharacterRequest) {
        BookCharacter existing = bookcharacterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("BookCharacter not found"));

    // Copier les champs simples
        existing.setName(bookcharacterRequest.getName());
        existing.setDescription(bookcharacterRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
        if (bookcharacterRequest.getBooks() != null) {
            existing.getBooks().clear();

            List<Book> booksList = bookcharacterRequest.getBooks().stream()
                .map(item -> booksRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("Book not found")))
                .collect(Collectors.toList());

            existing.getBooks().addAll(booksList);

            // Mettre à jour le côté inverse
            booksList.forEach(it -> {
                if (!it.getCharacters().contains(existing)) {
                    it.getCharacters().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------

    return bookcharacterRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<BookCharacter> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        BookCharacter entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
        if (entity.getBooks() != null) {
            for (Book item : new ArrayList<>(entity.getBooks())) {
                
                item.getCharacters().remove(entity); // retire côté inverse
                
            }
            entity.getBooks().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}
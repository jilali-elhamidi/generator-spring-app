package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.BookSeries;
import com.example.modules.entertainment_ecosystem.repository.BookSeriesRepository;
import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class BookSeriesService extends BaseService<BookSeries> {

    protected final BookSeriesRepository bookseriesRepository;
    private final BookRepository booksRepository;

    public BookSeriesService(BookSeriesRepository repository,BookRepository booksRepository)
    {
        super(repository);
        this.bookseriesRepository = repository;
        this.booksRepository = booksRepository;
    }

    @Override
    public BookSeries save(BookSeries bookseries) {


    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (bookseries.getBooks() != null) {
            List<Book> managedBooks = new ArrayList<>();
            for (Book item : bookseries.getBooks()) {
            if (item.getId() != null) {
            Book existingItem = booksRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setSeries(bookseries);
            managedBooks.add(existingItem);
            } else {
            item.setSeries(bookseries);
            managedBooks.add(item);
            }
            }
            bookseries.setBooks(managedBooks);
            }
        
    


    

    

        return bookseriesRepository.save(bookseries);
    }


    public BookSeries update(Long id, BookSeries bookseriesRequest) {
        BookSeries existing = bookseriesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("BookSeries not found"));

    // Copier les champs simples
        existing.setName(bookseriesRequest.getName());
        existing.setDescription(bookseriesRequest.getDescription());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée
        // Vider la collection existante
        existing.getBooks().clear();

        if (bookseriesRequest.getBooks() != null) {
        for (var item : bookseriesRequest.getBooks()) {
        Book existingItem;
        if (item.getId() != null) {
        existingItem = booksRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Book not found"));
        } else {
        existingItem = item; // ou mapper les champs si DTO
        }
        // Maintenir la relation bidirectionnelle
        existingItem.setSeries(existing);

        // Ajouter directement dans la collection existante
        existing.getBooks().add(existingItem);
        }
        }
        // NE PLUS FAIRE setCollection()

    


        return bookseriesRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<BookSeries> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

BookSeries entity = entityOpt.get();

// --- Dissocier OneToMany ---

    
        if (entity.getBooks() != null) {
        for (var child : entity.getBooks()) {
        
            child.setSeries(null); // retirer la référence inverse
        
        }
        entity.getBooks().clear();
        }
    


// --- Dissocier ManyToMany ---

    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}
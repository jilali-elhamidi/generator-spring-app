package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.BookSeries;
import com.example.modules.entertainment_ecosystem.repository.BookSeriesRepository;

import com.example.modules.entertainment_ecosystem.model.Book;
import com.example.modules.entertainment_ecosystem.repository.BookRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class BookSeriesService extends BaseService<BookSeries> {

    protected final BookSeriesRepository bookseriesRepository;
    
    protected final BookRepository booksRepository;
    

    public BookSeriesService(BookSeriesRepository repository, BookRepository booksRepository)
    {
        super(repository);
        this.bookseriesRepository = repository;
        
        this.booksRepository = booksRepository;
        
    }

    @Transactional
    @Override
    public BookSeries save(BookSeries bookseries) {
    // ---------- OneToMany ----------
        if (bookseries.getBooks() != null) {
            List<Book> managedBooks = new ArrayList<>();
            for (Book item : bookseries.getBooks()) {
                if (item.getId() != null) {
                    Book existingItem = booksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));

                     existingItem.setSeries(bookseries);
                     managedBooks.add(existingItem);
                } else {
                    item.setSeries(bookseries);
                    managedBooks.add(item);
                }
            }
            bookseries.setBooks(managedBooks);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return bookseriesRepository.save(bookseries);
}

    @Transactional
    @Override
    public BookSeries update(Long id, BookSeries bookseriesRequest) {
        BookSeries existing = bookseriesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("BookSeries not found"));

    // Copier les champs simples
        existing.setName(bookseriesRequest.getName());
        existing.setDescription(bookseriesRequest.getDescription());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getBooks().clear();

        if (bookseriesRequest.getBooks() != null) {
            for (var item : bookseriesRequest.getBooks()) {
                Book existingItem;
                if (item.getId() != null) {
                    existingItem = booksRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                } else {
                existingItem = item;
                }

                existingItem.setSeries(existing);
                existing.getBooks().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return bookseriesRepository.save(existing);
}

    // Pagination simple
    public Page<BookSeries> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<BookSeries> search(Map<String, String> filters, Pageable pageable) {
        return super.search(BookSeries.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<BookSeries> saveAll(List<BookSeries> bookseriesList) {
        return super.saveAll(bookseriesList);
    }

}
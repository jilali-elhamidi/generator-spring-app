package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends BaseRepository<Book, Long> {
}

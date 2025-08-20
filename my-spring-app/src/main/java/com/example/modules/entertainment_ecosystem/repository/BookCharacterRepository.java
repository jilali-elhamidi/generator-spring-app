package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.BookCharacter;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCharacterRepository extends BaseRepository<BookCharacter, Long> {

}

package com.example.modules.entertainment_ecosystem.repository;

import com.example.core.repository.BaseRepository;
import com.example.modules.entertainment_ecosystem.model.ReviewReply;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewReplyRepository extends BaseRepository<ReviewReply, Long> {
}

package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.ForumThread;
import com.example.modules.entertainment_ecosystem.repository.ForumThreadRepository;

import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;

import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;

import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import com.example.modules.entertainment_ecosystem.repository.ReportedContentRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ForumPostService extends BaseService<ForumPost> {

    protected final ForumPostRepository forumpostRepository;
    
    protected final UserProfileRepository authorRepository;
    
    protected final ForumThreadRepository threadRepository;
    
    protected final ForumPostRepository repliesRepository;
    
    protected final ForumPostRepository parentPostRepository;
    
    protected final ReportedContentRepository reportedContentRepository;
    

    public ForumPostService(ForumPostRepository repository, UserProfileRepository authorRepository, ForumThreadRepository threadRepository, ForumPostRepository repliesRepository, ForumPostRepository parentPostRepository, ReportedContentRepository reportedContentRepository)
    {
        super(repository);
        this.forumpostRepository = repository;
        
        this.authorRepository = authorRepository;
        
        this.threadRepository = threadRepository;
        
        this.repliesRepository = repliesRepository;
        
        this.parentPostRepository = parentPostRepository;
        
        this.reportedContentRepository = reportedContentRepository;
        
    }

    @Transactional
    @Override
    public ForumPost save(ForumPost forumpost) {
    // ---------- OneToMany ----------
        if (forumpost.getReplies() != null) {
            List<ForumPost> managedReplies = new ArrayList<>();
            for (ForumPost item : forumpost.getReplies()) {
                if (item.getId() != null) {
                    ForumPost existingItem = repliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumPost not found"));

                     existingItem.setParentPost(forumpost);
                     managedReplies.add(existingItem);
                } else {
                    item.setParentPost(forumpost);
                    managedReplies.add(item);
                }
            }
            forumpost.setReplies(managedReplies);
        }
    
        if (forumpost.getReportedContent() != null) {
            List<ReportedContent> managedReportedContent = new ArrayList<>();
            for (ReportedContent item : forumpost.getReportedContent()) {
                if (item.getId() != null) {
                    ReportedContent existingItem = reportedContentRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReportedContent not found"));

                     existingItem.setForumPost(forumpost);
                     managedReportedContent.add(existingItem);
                } else {
                    item.setForumPost(forumpost);
                    managedReportedContent.add(item);
                }
            }
            forumpost.setReportedContent(managedReportedContent);
        }
    
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (forumpost.getAuthor() != null) {
            if (forumpost.getAuthor().getId() != null) {
                UserProfile existingAuthor = authorRepository.findById(
                    forumpost.getAuthor().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + forumpost.getAuthor().getId()));
                forumpost.setAuthor(existingAuthor);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newAuthor = authorRepository.save(forumpost.getAuthor());
                forumpost.setAuthor(newAuthor);
            }
        }
        
        if (forumpost.getThread() != null) {
            if (forumpost.getThread().getId() != null) {
                ForumThread existingThread = threadRepository.findById(
                    forumpost.getThread().getId()
                ).orElseThrow(() -> new RuntimeException("ForumThread not found with id "
                    + forumpost.getThread().getId()));
                forumpost.setThread(existingThread);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ForumThread newThread = threadRepository.save(forumpost.getThread());
                forumpost.setThread(newThread);
            }
        }
        
        if (forumpost.getParentPost() != null) {
            if (forumpost.getParentPost().getId() != null) {
                ForumPost existingParentPost = parentPostRepository.findById(
                    forumpost.getParentPost().getId()
                ).orElseThrow(() -> new RuntimeException("ForumPost not found with id "
                    + forumpost.getParentPost().getId()));
                forumpost.setParentPost(existingParentPost);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ForumPost newParentPost = parentPostRepository.save(forumpost.getParentPost());
                forumpost.setParentPost(newParentPost);
            }
        }
        
    // ---------- OneToOne ----------
    return forumpostRepository.save(forumpost);
}

    @Transactional
    @Override
    public ForumPost update(Long id, ForumPost forumpostRequest) {
        ForumPost existing = forumpostRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ForumPost not found"));

    // Copier les champs simples
        existing.setContent(forumpostRequest.getContent());
        existing.setPostDate(forumpostRequest.getPostDate());

    // ---------- Relations ManyToOne ----------
        if (forumpostRequest.getAuthor() != null &&
            forumpostRequest.getAuthor().getId() != null) {

            UserProfile existingAuthor = authorRepository.findById(
                forumpostRequest.getAuthor().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setAuthor(existingAuthor);
        } else {
            existing.setAuthor(null);
        }
        
        if (forumpostRequest.getThread() != null &&
            forumpostRequest.getThread().getId() != null) {

            ForumThread existingThread = threadRepository.findById(
                forumpostRequest.getThread().getId()
            ).orElseThrow(() -> new RuntimeException("ForumThread not found"));

            existing.setThread(existingThread);
        } else {
            existing.setThread(null);
        }
        
        if (forumpostRequest.getParentPost() != null &&
            forumpostRequest.getParentPost().getId() != null) {

            ForumPost existingParentPost = parentPostRepository.findById(
                forumpostRequest.getParentPost().getId()
            ).orElseThrow(() -> new RuntimeException("ForumPost not found"));

            existing.setParentPost(existingParentPost);
        } else {
            existing.setParentPost(null);
        }
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
        existing.getReplies().clear();

        if (forumpostRequest.getReplies() != null) {
            for (var item : forumpostRequest.getReplies()) {
                ForumPost existingItem;
                if (item.getId() != null) {
                    existingItem = repliesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ForumPost not found"));
                } else {
                existingItem = item;
                }

                existingItem.setParentPost(existing);
                existing.getReplies().add(existingItem);
            }
        }
        
        existing.getReportedContent().clear();

        if (forumpostRequest.getReportedContent() != null) {
            for (var item : forumpostRequest.getReportedContent()) {
                ReportedContent existingItem;
                if (item.getId() != null) {
                    existingItem = reportedContentRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("ReportedContent not found"));
                } else {
                existingItem = item;
                }

                existingItem.setForumPost(existing);
                existing.getReportedContent().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return forumpostRepository.save(existing);
}

    // Pagination simple
    public Page<ForumPost> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ForumPost> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ForumPost.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ForumPost> saveAll(List<ForumPost> forumpostList) {
        return super.saveAll(forumpostList);
    }

}
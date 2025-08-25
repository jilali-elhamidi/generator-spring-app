package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ReportedContent;
import com.example.modules.entertainment_ecosystem.repository.ReportedContentRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.Review;
import com.example.modules.entertainment_ecosystem.repository.ReviewRepository;
import com.example.modules.entertainment_ecosystem.model.ForumPost;
import com.example.modules.entertainment_ecosystem.repository.ForumPostRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReportedContentService extends BaseService<ReportedContent> {

    protected final ReportedContentRepository reportedcontentRepository;
    private final UserProfileRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ForumPostRepository forumPostRepository;

    public ReportedContentService(ReportedContentRepository repository, UserProfileRepository userRepository, ReviewRepository reviewRepository, ForumPostRepository forumPostRepository)
    {
        super(repository);
        this.reportedcontentRepository = repository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.forumPostRepository = forumPostRepository;
    }

    @Override
    public ReportedContent save(ReportedContent reportedcontent) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (reportedcontent.getUser() != null) {
            if (reportedcontent.getUser().getId() != null) {
                UserProfile existingUser = userRepository.findById(
                    reportedcontent.getUser().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + reportedcontent.getUser().getId()));
                reportedcontent.setUser(existingUser);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newUser = userRepository.save(reportedcontent.getUser());
                reportedcontent.setUser(newUser);
            }
        }
        
        if (reportedcontent.getReview() != null) {
            if (reportedcontent.getReview().getId() != null) {
                Review existingReview = reviewRepository.findById(
                    reportedcontent.getReview().getId()
                ).orElseThrow(() -> new RuntimeException("Review not found with id "
                    + reportedcontent.getReview().getId()));
                reportedcontent.setReview(existingReview);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Review newReview = reviewRepository.save(reportedcontent.getReview());
                reportedcontent.setReview(newReview);
            }
        }
        
        if (reportedcontent.getForumPost() != null) {
            if (reportedcontent.getForumPost().getId() != null) {
                ForumPost existingForumPost = forumPostRepository.findById(
                    reportedcontent.getForumPost().getId()
                ).orElseThrow(() -> new RuntimeException("ForumPost not found with id "
                    + reportedcontent.getForumPost().getId()));
                reportedcontent.setForumPost(existingForumPost);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                ForumPost newForumPost = forumPostRepository.save(reportedcontent.getForumPost());
                reportedcontent.setForumPost(newForumPost);
            }
        }
        
    // ---------- OneToOne ----------
    return reportedcontentRepository.save(reportedcontent);
}


    public ReportedContent update(Long id, ReportedContent reportedcontentRequest) {
        ReportedContent existing = reportedcontentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ReportedContent not found"));

    // Copier les champs simples
        existing.setReason(reportedcontentRequest.getReason());
        existing.setReportDate(reportedcontentRequest.getReportDate());
        existing.setStatus(reportedcontentRequest.getStatus());

    // ---------- Relations ManyToOne ----------
        if (reportedcontentRequest.getUser() != null &&
            reportedcontentRequest.getUser().getId() != null) {

            UserProfile existingUser = userRepository.findById(
                reportedcontentRequest.getUser().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setUser(existingUser);
        } else {
            existing.setUser(null);
        }
        
        if (reportedcontentRequest.getReview() != null &&
            reportedcontentRequest.getReview().getId() != null) {

            Review existingReview = reviewRepository.findById(
                reportedcontentRequest.getReview().getId()
            ).orElseThrow(() -> new RuntimeException("Review not found"));

            existing.setReview(existingReview);
        } else {
            existing.setReview(null);
        }
        
        if (reportedcontentRequest.getForumPost() != null &&
            reportedcontentRequest.getForumPost().getId() != null) {

            ForumPost existingForumPost = forumPostRepository.findById(
                reportedcontentRequest.getForumPost().getId()
            ).orElseThrow(() -> new RuntimeException("ForumPost not found"));

            existing.setForumPost(existingForumPost);
        } else {
            existing.setForumPost(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return reportedcontentRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<ReportedContent> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        ReportedContent entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getUser() != null) {
            entity.setUser(null);
        }
        
        if (entity.getReview() != null) {
            entity.setReview(null);
        }
        
        if (entity.getForumPost() != null) {
            entity.setForumPost(null);
        }
        
        repository.delete(entity);
        return true;
    }
    @Transactional
    public List<ReportedContent> saveAll(List<ReportedContent> reportedcontentList) {

        return reportedcontentRepository.saveAll(reportedcontentList);
    }

}
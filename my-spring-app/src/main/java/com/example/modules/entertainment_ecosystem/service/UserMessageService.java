package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.repository.UserMessageRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.repository.MessageThreadRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserMessageService extends BaseService<UserMessage> {

    protected final UserMessageRepository usermessageRepository;
    private final UserProfileRepository senderRepository;
    private final UserProfileRepository receiverRepository;
    private final MessageThreadRepository threadRepository;

    public UserMessageService(UserMessageRepository repository, UserProfileRepository senderRepository, UserProfileRepository receiverRepository, MessageThreadRepository threadRepository)
    {
        super(repository);
        this.usermessageRepository = repository;
        this.senderRepository = senderRepository;
        this.receiverRepository = receiverRepository;
        this.threadRepository = threadRepository;
    }

    @Override
    public UserMessage save(UserMessage usermessage) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (usermessage.getSender() != null) {
            if (usermessage.getSender().getId() != null) {
                UserProfile existingSender = senderRepository.findById(
                    usermessage.getSender().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + usermessage.getSender().getId()));
                usermessage.setSender(existingSender);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newSender = senderRepository.save(usermessage.getSender());
                usermessage.setSender(newSender);
            }
        }
        
        if (usermessage.getReceiver() != null) {
            if (usermessage.getReceiver().getId() != null) {
                UserProfile existingReceiver = receiverRepository.findById(
                    usermessage.getReceiver().getId()
                ).orElseThrow(() -> new RuntimeException("UserProfile not found with id "
                    + usermessage.getReceiver().getId()));
                usermessage.setReceiver(existingReceiver);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                UserProfile newReceiver = receiverRepository.save(usermessage.getReceiver());
                usermessage.setReceiver(newReceiver);
            }
        }
        
        if (usermessage.getThread() != null) {
            if (usermessage.getThread().getId() != null) {
                MessageThread existingThread = threadRepository.findById(
                    usermessage.getThread().getId()
                ).orElseThrow(() -> new RuntimeException("MessageThread not found with id "
                    + usermessage.getThread().getId()));
                usermessage.setThread(existingThread);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                MessageThread newThread = threadRepository.save(usermessage.getThread());
                usermessage.setThread(newThread);
            }
        }
        
    // ---------- OneToOne ----------
    return usermessageRepository.save(usermessage);
}


    public UserMessage update(Long id, UserMessage usermessageRequest) {
        UserMessage existing = usermessageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("UserMessage not found"));

    // Copier les champs simples
        existing.setSubject(usermessageRequest.getSubject());
        existing.setBody(usermessageRequest.getBody());
        existing.setSentDate(usermessageRequest.getSentDate());
        existing.setIsRead(usermessageRequest.getIsRead());

    // ---------- Relations ManyToOne ----------
        if (usermessageRequest.getSender() != null &&
            usermessageRequest.getSender().getId() != null) {

            UserProfile existingSender = senderRepository.findById(
                usermessageRequest.getSender().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setSender(existingSender);
        } else {
            existing.setSender(null);
        }
        
        if (usermessageRequest.getReceiver() != null &&
            usermessageRequest.getReceiver().getId() != null) {

            UserProfile existingReceiver = receiverRepository.findById(
                usermessageRequest.getReceiver().getId()
            ).orElseThrow(() -> new RuntimeException("UserProfile not found"));

            existing.setReceiver(existingReceiver);
        } else {
            existing.setReceiver(null);
        }
        
        if (usermessageRequest.getThread() != null &&
            usermessageRequest.getThread().getId() != null) {

            MessageThread existingThread = threadRepository.findById(
                usermessageRequest.getThread().getId()
            ).orElseThrow(() -> new RuntimeException("MessageThread not found"));

            existing.setThread(existingThread);
        } else {
            existing.setThread(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return usermessageRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<UserMessage> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        UserMessage entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getSender() != null) {
            entity.setSender(null);
        }
        
        if (entity.getReceiver() != null) {
            entity.setReceiver(null);
        }
        
        if (entity.getThread() != null) {
            entity.setThread(null);
        }
        
        repository.delete(entity);
        return true;
    }
}
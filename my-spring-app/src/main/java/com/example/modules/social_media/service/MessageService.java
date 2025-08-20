package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.repository.MessageRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MessageService extends BaseService<Message> {

    protected final MessageRepository messageRepository;
    private final ProfileRepository senderRepository;
    private final ProfileRepository recipientRepository;

    public MessageService(MessageRepository repository, ProfileRepository senderRepository, ProfileRepository recipientRepository)
    {
        super(repository);
        this.messageRepository = repository;
        this.senderRepository = senderRepository;
        this.recipientRepository = recipientRepository;
    }

    @Override
    public Message save(Message message) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (message.getSender() != null &&
            message.getSender().getId() != null) {

            Profile existingSender = senderRepository.findById(
                message.getSender().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            message.setSender(existingSender);
        }
        
        if (message.getRecipient() != null &&
            message.getRecipient().getId() != null) {

            Profile existingRecipient = recipientRepository.findById(
                message.getRecipient().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            message.setRecipient(existingRecipient);
        }
        
    // ---------- OneToOne ----------
    return messageRepository.save(message);
}


    public Message update(Long id, Message messageRequest) {
        Message existing = messageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Message not found"));

    // Copier les champs simples
        existing.setContent(messageRequest.getContent());
        existing.setSentDate(messageRequest.getSentDate());
        existing.setRead(messageRequest.getRead());

    // ---------- Relations ManyToOne ----------
        if (messageRequest.getSender() != null &&
            messageRequest.getSender().getId() != null) {

            Profile existingSender = senderRepository.findById(
                messageRequest.getSender().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            existing.setSender(existingSender);
        } else {
            existing.setSender(null);
        }
        
        if (messageRequest.getRecipient() != null &&
            messageRequest.getRecipient().getId() != null) {

            Profile existingRecipient = recipientRepository.findById(
                messageRequest.getRecipient().getId()
            ).orElseThrow(() -> new RuntimeException("Profile not found"));

            existing.setRecipient(existingRecipient);
        } else {
            existing.setRecipient(null);
        }
        
    // ---------- Relations ManyToOne ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return messageRepository.save(existing);
}
    @Transactional
    public boolean deleteById(Long id) {
        Optional<Message> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        Message entity = entityOpt.get();
    // --- Dissocier OneToMany ---
    // --- Dissocier ManyToMany ---
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        if (entity.getSender() != null) {
            entity.setSender(null);
        }
        
        if (entity.getRecipient() != null) {
            entity.setRecipient(null);
        }
        
        repository.delete(entity);
        return true;
    }
}
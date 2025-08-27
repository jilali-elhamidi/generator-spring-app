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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MessageService extends BaseService<Message> {

    protected final MessageRepository messageRepository;
    
    protected final ProfileRepository senderRepository;
    
    protected final ProfileRepository recipientRepository;
    

    public MessageService(MessageRepository repository, ProfileRepository senderRepository, ProfileRepository recipientRepository)
    {
        super(repository);
        this.messageRepository = repository;
        
        this.senderRepository = senderRepository;
        
        this.recipientRepository = recipientRepository;
        
    }

    @Transactional
    @Override
    public Message save(Message message) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
    // ---------- ManyToOne ----------
        if (message.getSender() != null) {
            if (message.getSender().getId() != null) {
                Profile existingSender = senderRepository.findById(
                    message.getSender().getId()
                ).orElseThrow(() -> new RuntimeException("Profile not found with id "
                    + message.getSender().getId()));
                message.setSender(existingSender);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Profile newSender = senderRepository.save(message.getSender());
                message.setSender(newSender);
            }
        }
        
        if (message.getRecipient() != null) {
            if (message.getRecipient().getId() != null) {
                Profile existingRecipient = recipientRepository.findById(
                    message.getRecipient().getId()
                ).orElseThrow(() -> new RuntimeException("Profile not found with id "
                    + message.getRecipient().getId()));
                message.setRecipient(existingRecipient);
            } else {
                // Nouvel objet ManyToOne → on le sauvegarde
                Profile newRecipient = recipientRepository.save(message.getRecipient());
                message.setRecipient(newRecipient);
            }
        }
        
    // ---------- OneToOne ----------
    return messageRepository.save(message);
}

    @Transactional
    @Override
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
        
    // ---------- Relations ManyToMany ----------
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return messageRepository.save(existing);
}

    // Pagination simple
    public Page<Message> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<Message> search(Map<String, String> filters, Pageable pageable) {
        return super.search(Message.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<Message> saveAll(List<Message> messageList) {
        return super.saveAll(messageList);
    }

}
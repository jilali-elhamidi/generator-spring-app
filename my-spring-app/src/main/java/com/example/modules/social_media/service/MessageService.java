package com.example.modules.social_media.service;

import com.example.core.service.BaseService;
import com.example.modules.social_media.model.Message;
import com.example.modules.social_media.repository.MessageRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;
import com.example.modules.social_media.model.Profile;
import com.example.modules.social_media.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MessageService extends BaseService<Message> {

    protected final MessageRepository messageRepository;
    private final ProfileRepository senderRepository;
    private final ProfileRepository recipientRepository;

    public MessageService(MessageRepository repository,ProfileRepository senderRepository,ProfileRepository recipientRepository)
    {
        super(repository);
        this.messageRepository = repository;
        this.senderRepository = senderRepository;
        this.recipientRepository = recipientRepository;
    }

    @Override
    public Message save(Message message) {

        if (message.getSender() != null && message.getSender().getId() != null) {
        Profile sender = senderRepository.findById(message.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        message.setSender(sender);
        }

        if (message.getRecipient() != null && message.getRecipient().getId() != null) {
        Profile recipient = recipientRepository.findById(message.getRecipient().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        message.setRecipient(recipient);
        }

        return messageRepository.save(message);
    }


    public Message update(Long id, Message messageRequest) {
        Message existing = messageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Message not found"));

    // Copier les champs simples
        existing.setContent(messageRequest.getContent());
        existing.setSentDate(messageRequest.getSentDate());
        existing.setRead(messageRequest.getRead());

// Relations ManyToOne : mise à jour conditionnelle

        if (messageRequest.getSender() != null && messageRequest.getSender().getId() != null) {
        Profile sender = senderRepository.findById(messageRequest.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        existing.setSender(sender);
        }

        if (messageRequest.getRecipient() != null && messageRequest.getRecipient().getId() != null) {
        Profile recipient = recipientRepository.findById(messageRequest.getRecipient().getId())
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        existing.setRecipient(recipient);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

        return messageRepository.save(existing);
    }
}
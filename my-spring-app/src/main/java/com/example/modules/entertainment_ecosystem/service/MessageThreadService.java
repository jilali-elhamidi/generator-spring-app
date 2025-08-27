package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.repository.MessageThreadRepository;

import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;

import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.repository.UserMessageRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class MessageThreadService extends BaseService<MessageThread> {

    protected final MessageThreadRepository messagethreadRepository;
    
    protected final UserProfileRepository participantsRepository;
    
    protected final UserMessageRepository messagesRepository;
    

    public MessageThreadService(MessageThreadRepository repository, UserProfileRepository participantsRepository, UserMessageRepository messagesRepository)
    {
        super(repository);
        this.messagethreadRepository = repository;
        
        this.participantsRepository = participantsRepository;
        
        this.messagesRepository = messagesRepository;
        
    }

    @Transactional
    @Override
    public MessageThread save(MessageThread messagethread) {
    // ---------- OneToMany ----------
        if (messagethread.getMessages() != null) {
            List<UserMessage> managedMessages = new ArrayList<>();
            for (UserMessage item : messagethread.getMessages()) {
                if (item.getId() != null) {
                    UserMessage existingItem = messagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserMessage not found"));

                     existingItem.setThread(messagethread);
                     managedMessages.add(existingItem);
                } else {
                    item.setThread(messagethread);
                    managedMessages.add(item);
                }
            }
            messagethread.setMessages(managedMessages);
        }
    
    // ---------- ManyToMany ----------
        if (messagethread.getParticipants() != null &&
            !messagethread.getParticipants().isEmpty()) {

            List<UserProfile> attachedParticipants = new ArrayList<>();
            for (UserProfile item : messagethread.getParticipants()) {
                if (item.getId() != null) {
                    UserProfile existingItem = participantsRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserProfile not found with id " + item.getId()));
                    attachedParticipants.add(existingItem);
                } else {

                    UserProfile newItem = participantsRepository.save(item);
                    attachedParticipants.add(newItem);
                }
            }

            messagethread.setParticipants(attachedParticipants);

            // côté propriétaire (UserProfile → MessageThread)
            attachedParticipants.forEach(it -> it.getMessageThreads().add(messagethread));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return messagethreadRepository.save(messagethread);
}

    @Transactional
    @Override
    public MessageThread update(Long id, MessageThread messagethreadRequest) {
        MessageThread existing = messagethreadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MessageThread not found"));

    // Copier les champs simples
        existing.setSubject(messagethreadRequest.getSubject());
        existing.setLastUpdated(messagethreadRequest.getLastUpdated());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
        if (messagethreadRequest.getParticipants() != null) {
            existing.getParticipants().clear();

            List<UserProfile> participantsList = messagethreadRequest.getParticipants().stream()
                .map(item -> participantsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());

            existing.getParticipants().addAll(participantsList);

            // Mettre à jour le côté inverse
            participantsList.forEach(it -> {
                if (!it.getMessageThreads().contains(existing)) {
                    it.getMessageThreads().add(existing);
                }
            });
        }
        
    // ---------- Relations OneToMany ----------
        existing.getMessages().clear();

        if (messagethreadRequest.getMessages() != null) {
            for (var item : messagethreadRequest.getMessages()) {
                UserMessage existingItem;
                if (item.getId() != null) {
                    existingItem = messagesRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("UserMessage not found"));
                } else {
                existingItem = item;
                }

                existingItem.setThread(existing);
                existing.getMessages().add(existingItem);
            }
        }
        
    // ---------- Relations OneToOne ----------
    return messagethreadRepository.save(existing);
}

    // Pagination simple
    public Page<MessageThread> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<MessageThread> search(Map<String, String> filters, Pageable pageable) {
        return super.search(MessageThread.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<MessageThread> saveAll(List<MessageThread> messagethreadList) {
        return super.saveAll(messagethreadList);
    }

}
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MessageThreadService extends BaseService<MessageThread> {

    protected final MessageThreadRepository messagethreadRepository;
    private final UserProfileRepository participantsRepository;
    private final UserMessageRepository messagesRepository;

    public MessageThreadService(MessageThreadRepository repository, UserProfileRepository participantsRepository, UserMessageRepository messagesRepository)
    {
        super(repository);
        this.messagethreadRepository = repository;
        this.participantsRepository = participantsRepository;
        this.messagesRepository = messagesRepository;
    }

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


    public MessageThread update(Long id, MessageThread messagethreadRequest) {
        MessageThread existing = messagethreadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MessageThread not found"));

    // Copier les champs simples
        existing.setSubject(messagethreadRequest.getSubject());
        existing.setLastUpdated(messagethreadRequest.getLastUpdated());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToOne ----------
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
    @Transactional
    public boolean deleteById(Long id) {
        Optional<MessageThread> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) return false;

        MessageThread entity = entityOpt.get();
    // --- Dissocier OneToMany ---
        if (entity.getMessages() != null) {
            for (var child : entity.getMessages()) {
                // retirer la référence inverse
                child.setThread(null);
            }
            entity.getMessages().clear();
        }
        
    // --- Dissocier ManyToMany ---
        if (entity.getParticipants() != null) {
            for (UserProfile item : new ArrayList<>(entity.getParticipants())) {
                
                item.getMessageThreads().remove(entity); // retire côté inverse
            }
            entity.getParticipants().clear(); // puis vide côté courant
        }
        
    // --- Dissocier OneToOne ---
    // --- Dissocier ManyToOne ---
        repository.delete(entity);
        return true;
    }
}
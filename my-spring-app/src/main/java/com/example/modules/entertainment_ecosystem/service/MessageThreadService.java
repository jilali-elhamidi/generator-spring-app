package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.repository.MessageThreadRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserMessage;
import com.example.modules.entertainment_ecosystem.repository.UserMessageRepository;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class MessageThreadService extends BaseService<MessageThread> {

    protected final MessageThreadRepository messagethreadRepository;
    private final UserProfileRepository participantsRepository;
    private final UserMessageRepository messagesRepository;

    public MessageThreadService(MessageThreadRepository repository,UserProfileRepository participantsRepository,UserMessageRepository messagesRepository)
    {
        super(repository);
        this.messagethreadRepository = repository;
        this.participantsRepository = participantsRepository;
        this.messagesRepository = messagesRepository;
    }

    @Override
    public MessageThread save(MessageThread messagethread) {


    

    
        // Cherche la relation ManyToOne correspondante dans l'entité enfant
        
            if (messagethread.getMessages() != null) {
            List<UserMessage> managedMessages = new ArrayList<>();
            for (UserMessage item : messagethread.getMessages()) {
            if (item.getId() != null) {
            UserMessage existingItem = messagesRepository.findById(item.getId())
            .orElseThrow(() -> new RuntimeException("UserMessage not found"));
            // Utilise le nom du champ ManyToOne côté enfant pour le setter
            existingItem.setThread(messagethread);
            managedMessages.add(existingItem);
            } else {
            item.setThread(messagethread);
            managedMessages.add(item);
            }
            }
            messagethread.setMessages(managedMessages);
            }
        
    

    
    

        return messagethreadRepository.save(messagethread);
    }


    public MessageThread update(Long id, MessageThread messagethreadRequest) {
        MessageThread existing = messagethreadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MessageThread not found"));

    // Copier les champs simples
        existing.setSubject(messagethreadRequest.getSubject());
        existing.setLastUpdated(messagethreadRequest.getLastUpdated());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

        if (messagethreadRequest.getParticipants() != null) {
            existing.getParticipants().clear();
            List<UserProfile> participantsList = messagethreadRequest.getParticipants().stream()
                .map(item -> participantsRepository.findById(item.getId())
                    .orElseThrow(() -> new RuntimeException("UserProfile not found")))
                .collect(Collectors.toList());
        existing.getParticipants().addAll(participantsList);
        }

// Relations OneToMany : synchronisation sécurisée
        existing.getMessages().clear();

        if (messagethreadRequest.getMessages() != null) {
        List<UserMessage> managedMessages = new ArrayList<>();

        for (var item : messagethreadRequest.getMessages()) {
        if (item.getId() != null) {
        UserMessage existingItem = messagesRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("UserMessage not found"));
        existingItem.setThread(existing);
        managedMessages.add(existingItem);
        } else {
        item.setThread(existing);
        managedMessages.add(item);
        }
        }
        existing.setMessages(managedMessages);
        }

    

    


        return messagethreadRepository.save(existing);
    }


}
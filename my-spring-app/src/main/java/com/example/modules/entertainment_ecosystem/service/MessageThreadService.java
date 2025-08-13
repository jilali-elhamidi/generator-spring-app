package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.MessageThread;
import com.example.modules.entertainment_ecosystem.repository.MessageThreadRepository;
import com.example.modules.entertainment_ecosystem.model.UserProfile;
import com.example.modules.entertainment_ecosystem.repository.UserProfileRepository;
import com.example.modules.entertainment_ecosystem.model.UserMessage;

import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MessageThreadService extends BaseService<MessageThread> {

    protected final MessageThreadRepository messagethreadRepository;
    private final UserProfileRepository participantsRepository;

    public MessageThreadService(MessageThreadRepository repository,UserProfileRepository participantsRepository)
    {
        super(repository);
        this.messagethreadRepository = repository;
        this.participantsRepository = participantsRepository;
    }

    @Override
    public MessageThread save(MessageThread messagethread) {

        if (messagethread.getMessages() != null) {
            for (UserMessage item : messagethread.getMessages()) {
            item.setThread(messagethread);
            }
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
            for (var item : messagethreadRequest.getMessages()) {
            item.setThread(existing);
            existing.getMessages().add(item);
            }
        }

    

    


        return messagethreadRepository.save(existing);
    }
}
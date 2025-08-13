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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserMessageService extends BaseService<UserMessage> {

    protected final UserMessageRepository usermessageRepository;
    private final UserProfileRepository senderRepository;
    private final UserProfileRepository receiverRepository;
    private final MessageThreadRepository threadRepository;

    public UserMessageService(UserMessageRepository repository,UserProfileRepository senderRepository,UserProfileRepository receiverRepository,MessageThreadRepository threadRepository)
    {
        super(repository);
        this.usermessageRepository = repository;
        this.senderRepository = senderRepository;
        this.receiverRepository = receiverRepository;
        this.threadRepository = threadRepository;
    }

    @Override
    public UserMessage save(UserMessage usermessage) {

        if (usermessage.getSender() != null && usermessage.getSender().getId() != null) {
        UserProfile sender = senderRepository.findById(usermessage.getSender().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        usermessage.setSender(sender);
        }

        if (usermessage.getReceiver() != null && usermessage.getReceiver().getId() != null) {
        UserProfile receiver = receiverRepository.findById(usermessage.getReceiver().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        usermessage.setReceiver(receiver);
        }

        if (usermessage.getThread() != null && usermessage.getThread().getId() != null) {
        MessageThread thread = threadRepository.findById(usermessage.getThread().getId())
                .orElseThrow(() -> new RuntimeException("MessageThread not found"));
        usermessage.setThread(thread);
        }

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

// Relations ManyToOne : mise à jour conditionnelle

        if (usermessageRequest.getSender() != null && usermessageRequest.getSender().getId() != null) {
        UserProfile sender = senderRepository.findById(usermessageRequest.getSender().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setSender(sender);
        }

        if (usermessageRequest.getReceiver() != null && usermessageRequest.getReceiver().getId() != null) {
        UserProfile receiver = receiverRepository.findById(usermessageRequest.getReceiver().getId())
                .orElseThrow(() -> new RuntimeException("UserProfile not found"));
        existing.setReceiver(receiver);
        }

        if (usermessageRequest.getThread() != null && usermessageRequest.getThread().getId() != null) {
        MessageThread thread = threadRepository.findById(usermessageRequest.getThread().getId())
                .orElseThrow(() -> new RuntimeException("MessageThread not found"));
        existing.setThread(thread);
        }

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

    

    


        return usermessageRepository.save(existing);
    }
}
package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ArtistGroup;
import com.example.modules.entertainment_ecosystem.repository.ArtistGroupRepository;
import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ArtistGroupService extends BaseService<ArtistGroup> {

    protected final ArtistGroupRepository artistgroupRepository;
    private final ArtistRepository membersRepository;

    public ArtistGroupService(ArtistGroupRepository repository,ArtistRepository membersRepository)
    {
        super(repository);
        this.artistgroupRepository = repository;
        this.membersRepository = membersRepository;
    }

    @Override
    public ArtistGroup save(ArtistGroup artistgroup) {


    


    
        if (artistgroup.getMembers() != null
        && !artistgroup.getMembers().isEmpty()) {

        List<Artist> attachedMembers = artistgroup.getMembers().stream()
        .map(item -> membersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Artist not found with id " + item.getId())))
        .toList();

        artistgroup.setMembers(attachedMembers);

        // côté propriétaire (Artist → ArtistGroup)
        attachedMembers.forEach(it -> it.getGroups().add(artistgroup));
        }
    

    

        return artistgroupRepository.save(artistgroup);
    }


    public ArtistGroup update(Long id, ArtistGroup artistgroupRequest) {
        ArtistGroup existing = artistgroupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ArtistGroup not found"));

    // Copier les champs simples
        existing.setName(artistgroupRequest.getName());
        existing.setFormationDate(artistgroupRequest.getFormationDate());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée
        if (artistgroupRequest.getMembers() != null) {
        existing.getMembers().clear();

        List<Artist> membersList = artistgroupRequest.getMembers().stream()
        .map(item -> membersRepository.findById(item.getId())
        .orElseThrow(() -> new RuntimeException("Artist not found")))
        .collect(Collectors.toList());

        existing.getMembers().addAll(membersList);

        // Mettre à jour le côté inverse
        membersList.forEach(it -> {
        if (!it.getGroups().contains(existing)) {
        it.getGroups().add(existing);
        }
        });
        }

// Relations OneToMany : synchronisation sécurisée

    


        return artistgroupRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<ArtistGroup> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

ArtistGroup entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    
        if (entity.getMembers() != null) {
        for (Artist item : new ArrayList<>(entity.getMembers())) {
        
            item.getGroups().remove(entity); // retire côté inverse
        
        }
        entity.getMembers().clear(); // puis vide côté courant
        }
    



// --- Dissocier OneToOne ---

    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}
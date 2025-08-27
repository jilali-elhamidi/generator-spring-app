package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.ArtistGroup;
import com.example.modules.entertainment_ecosystem.repository.ArtistGroupRepository;

import com.example.modules.entertainment_ecosystem.model.Artist;
import com.example.modules.entertainment_ecosystem.repository.ArtistRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class ArtistGroupService extends BaseService<ArtistGroup> {

    protected final ArtistGroupRepository artistgroupRepository;
    
    protected final ArtistRepository membersRepository;
    

    public ArtistGroupService(ArtistGroupRepository repository, ArtistRepository membersRepository)
    {
        super(repository);
        this.artistgroupRepository = repository;
        
        this.membersRepository = membersRepository;
        
    }

    @Transactional
    @Override
    public ArtistGroup save(ArtistGroup artistgroup) {
    // ---------- OneToMany ----------
    // ---------- ManyToMany ----------
        if (artistgroup.getMembers() != null &&
            !artistgroup.getMembers().isEmpty()) {

            List<Artist> attachedMembers = new ArrayList<>();
            for (Artist item : artistgroup.getMembers()) {
                if (item.getId() != null) {
                    Artist existingItem = membersRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("Artist not found with id " + item.getId()));
                    attachedMembers.add(existingItem);
                } else {

                    Artist newItem = membersRepository.save(item);
                    attachedMembers.add(newItem);
                }
            }

            artistgroup.setMembers(attachedMembers);

            // côté propriétaire (Artist → ArtistGroup)
            attachedMembers.forEach(it -> it.getGroups().add(artistgroup));
        }
        
    // ---------- ManyToOne ----------
    // ---------- OneToOne ----------
    return artistgroupRepository.save(artistgroup);
}

    @Transactional
    @Override
    public ArtistGroup update(Long id, ArtistGroup artistgroupRequest) {
        ArtistGroup existing = artistgroupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ArtistGroup not found"));

    // Copier les champs simples
        existing.setName(artistgroupRequest.getName());
        existing.setFormationDate(artistgroupRequest.getFormationDate());

    // ---------- Relations ManyToOne ----------
    // ---------- Relations ManyToMany ----------
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
        
    // ---------- Relations OneToMany ----------
    // ---------- Relations OneToOne ----------
    return artistgroupRepository.save(existing);
}

    // Pagination simple
    public Page<ArtistGroup> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    // Recherche dynamique déléguée au BaseService (Specifications + pagination)
    public Page<ArtistGroup> search(Map<String, String> filters, Pageable pageable) {
        return super.search(ArtistGroup.class, filters, pageable);
    }

    @Transactional
    public boolean deleteById(Long id) {
        return super.deleteById(id);
    }

    @Transactional
    public List<ArtistGroup> saveAll(List<ArtistGroup> artistgroupList) {
        return super.saveAll(artistgroupList);
    }

}
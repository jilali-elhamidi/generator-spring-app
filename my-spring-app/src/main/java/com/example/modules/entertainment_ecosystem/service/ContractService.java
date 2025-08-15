package com.example.modules.entertainment_ecosystem.service;

import com.example.core.service.BaseService;
import com.example.modules.entertainment_ecosystem.model.Contract;
import com.example.modules.entertainment_ecosystem.repository.ContractRepository;
import com.example.modules.entertainment_ecosystem.model.EventSponsorship;
import com.example.modules.entertainment_ecosystem.repository.EventSponsorshipRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ContractService extends BaseService<Contract> {

    protected final ContractRepository contractRepository;
    private final EventSponsorshipRepository sponsorshipRepository;

    public ContractService(ContractRepository repository,EventSponsorshipRepository sponsorshipRepository)
    {
        super(repository);
        this.contractRepository = repository;
        this.sponsorshipRepository = sponsorshipRepository;
    }

    @Override
    public Contract save(Contract contract) {


    

    
        if (contract.getSponsorship() != null) {
        
        
            // Vérifier si l'entité est déjà persistée
            contract.setSponsorship(
            sponsorshipRepository.findById(contract.getSponsorship().getId())
            .orElseThrow(() -> new RuntimeException("sponsorship not found"))
            );
        
        contract.getSponsorship().setContract(contract);
        }

    


        return contractRepository.save(contract);
    }


    public Contract update(Long id, Contract contractRequest) {
        Contract existing = contractRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contract not found"));

    // Copier les champs simples
        existing.setContractNumber(contractRequest.getContractNumber());
        existing.setTerms(contractRequest.getTerms());

// Relations ManyToOne : mise à jour conditionnelle

// Relations ManyToMany : synchronisation sécurisée

// Relations OneToMany : synchronisation sécurisée

    

        if (contractRequest.getSponsorship() != null
        && contractRequest.getSponsorship().getId() != null) {

        EventSponsorship sponsorship = sponsorshipRepository.findById(
        contractRequest.getSponsorship().getId()
        ).orElseThrow(() -> new RuntimeException("EventSponsorship not found"));

        // Mise à jour de la relation côté propriétaire
        existing.setSponsorship(sponsorship);

        // Si la relation est bidirectionnelle et que le champ inverse existe
        
            sponsorship.setContract(existing);
        
        }

    


        return contractRepository.save(existing);
    }
@Transactional
public boolean deleteById(Long id) {
Optional<Contract> entityOpt = repository.findById(id);
if (entityOpt.isEmpty()) return false;

Contract entity = entityOpt.get();

// --- Dissocier OneToMany ---

    


// --- Dissocier ManyToMany ---

    


// --- Dissocier OneToOne ---

    
        if (entity.getSponsorship() != null) {
        // Dissocier côté inverse automatiquement
        entity.getSponsorship().setContract(null);
        // Dissocier côté direct
        entity.setSponsorship(null);
        }
    


// --- Dissocier ManyToOne ---

    


repository.delete(entity);
return true;
}
}
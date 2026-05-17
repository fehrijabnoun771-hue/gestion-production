package com.gestion.production.services;

import com.gestion.production.entities.OrdreFabrication;
import com.gestion.production.repositories.OrdreFabricationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrdreFabricationService {

    @Autowired
    private OrdreFabricationRepository ordreFabricationRepository;

    public List<OrdreFabrication> getAllOrdres() {
        return ordreFabricationRepository.findAll();
    }

    public Optional<OrdreFabrication> getOrdreById(Long id) {
        return ordreFabricationRepository.findById(id);
    }

    public OrdreFabrication createOrdre(OrdreFabrication ordre) {
        return ordreFabricationRepository.save(ordre);
    }

    public OrdreFabrication updateOrdre(Long id, OrdreFabrication ordre) {
        if (ordreFabricationRepository.existsById(id)) {
            ordre.setId(id);
            return ordreFabricationRepository.save(ordre);
        }
        return null;
    }

    public void deleteOrdre(Long id) {
        ordreFabricationRepository.deleteById(id);
    }

    // Récupère les ordres par statut
    public List<OrdreFabrication> getOrdresByStatut(String statut) {
        return ordreFabricationRepository.findByStatut(statut);
    }

    // Récupère les ordres urgents
    public List<OrdreFabrication> getOrdresUrgents() {
        return ordreFabricationRepository.findOrdresUrgents();
    }

    // Récupère les ordres par machine
    public List<OrdreFabrication> getOrdresByMachine(Long machineId) {
        return ordreFabricationRepository.findByMachineId(machineId);
    }

    // Change le statut d'un ordre
    public OrdreFabrication changeStatut(Long id, String statut) {
        Optional<OrdreFabrication> ordre = ordreFabricationRepository.findById(id);
        if (ordre.isPresent()) {
            ordre.get().setStatut(statut);
            return ordreFabricationRepository.save(ordre.get());
        }
        return null;
    }
}
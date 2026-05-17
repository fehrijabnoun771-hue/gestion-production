package com.gestion.production.services;

import com.gestion.production.entities.HistoriqueProduction;
import com.gestion.production.repositories.HistoriqueProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HistoriqueProductionService {

    @Autowired
    private HistoriqueProductionRepository historiqueRepository;

    public List<HistoriqueProduction> getAllHistoriques() {
        return historiqueRepository.findAll();
    }

    public Optional<HistoriqueProduction> getHistoriqueById(Long id) {
        return historiqueRepository.findById(id);
    }

    public HistoriqueProduction createHistorique(HistoriqueProduction historique) {
        return historiqueRepository.save(historique);
    }

    public void deleteHistorique(Long id) {
        historiqueRepository.deleteById(id);
    }

    // Récupère l'historique d'un ordre
    public List<HistoriqueProduction> getHistoriqueByOrdre(Long ordreId) {
        return historiqueRepository.findByOrdreId(ordreId);
    }

    // Récupère l'historique d'un utilisateur
    public List<HistoriqueProduction> getHistoriqueByUtilisateur(Long utilisateurId) {
        return historiqueRepository.findByUtilisateurId(utilisateurId);
    }
}
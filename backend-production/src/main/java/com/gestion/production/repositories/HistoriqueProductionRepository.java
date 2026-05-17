package com.gestion.production.repositories;

import com.gestion.production.entities.HistoriqueProduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoriqueProductionRepository 
    extends JpaRepository<HistoriqueProduction, Long> {

    // Trouve l'historique d'un ordre de fabrication
    List<HistoriqueProduction> findByOrdreId(Long ordreId);

    // Trouve l'historique d'un utilisateur
    List<HistoriqueProduction> findByUtilisateurId(Long utilisateurId);

    // Trouve l'historique par action
    // ex: CREATION, MODIFICATION, ANNULATION
    List<HistoriqueProduction> findByAction(String action);
}
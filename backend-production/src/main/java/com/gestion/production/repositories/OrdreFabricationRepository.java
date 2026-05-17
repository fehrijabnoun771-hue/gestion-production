package com.gestion.production.repositories;

import com.gestion.production.entities.OrdreFabrication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdreFabricationRepository 
    extends JpaRepository<OrdreFabrication, Long> {

    // Trouve les ordres par statut
    // ex: EN_ATTENTE, EN_COURS, TERMINE, ANNULE
    List<OrdreFabrication> findByStatut(String statut);

    // Trouve les ordres par priorité
    // ex: BASSE, NORMALE, HAUTE, URGENTE
    List<OrdreFabrication> findByPriorite(String priorite);

    // Trouve les ordres par machine
    List<OrdreFabrication> findByMachineId(Long machineId);

    // Trouve les ordres par technicien
    List<OrdreFabrication> findByTechnicienId(Long technicienId);

    // Trouve les ordres par produit
    List<OrdreFabrication> findByProduitId(Long produitId);

    // Trouve les ordres urgents en attente
    @Query("SELECT o FROM OrdreFabrication o WHERE o.priorite = 'URGENTE' AND o.statut = 'EN_ATTENTE'")
    List<OrdreFabrication> findOrdresUrgents();
}
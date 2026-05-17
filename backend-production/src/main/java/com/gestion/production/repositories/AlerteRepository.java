package com.gestion.production.repositories;

import com.gestion.production.entities.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlerteRepository 
    extends JpaRepository<Alerte, Long> {

    // Trouve les alertes non lues
    // SELECT * FROM alerte WHERE statut = 'NON_LU'
    List<Alerte> findByStatut(String statut);

    // Trouve les alertes par type
    List<Alerte> findByType(String type);

    // Trouve les alertes d'une machine
    List<Alerte> findByMachineId(Long machineId);

    // Trouve les alertes d'un produit
    List<Alerte> findByProduitId(Long produitId);

    // Compte les alertes non lues
    // SELECT COUNT(*) FROM alerte WHERE statut = 'NON_LU'
    @Query("SELECT COUNT(a) FROM Alerte a WHERE a.statut = 'NON_LU'")
    Long countAlertesNonLues();
}
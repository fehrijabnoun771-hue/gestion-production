package com.gestion.production.repositories;

import com.gestion.production.entities.PieceRechange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PieceRechangeRepository 
    extends JpaRepository<PieceRechange, Long> {

    // Trouve une pièce par référence
    Optional<PieceRechange> findByReference(String reference);

    // Trouve les pièces par nom
    List<PieceRechange> findByNomContaining(String nom);

    // Trouve les pièces d'un fournisseur
    List<PieceRechange> findByFournisseurId(Long fournisseurId);

    // Trouve les pièces avec stock faible
    List<PieceRechange> findByStockLessThan(Integer stock);

    // Pièces avec stock épuisé
    @Query("SELECT p FROM PieceRechange p WHERE p.stock = 0")
    List<PieceRechange> findPiecesStockEpuise();
}
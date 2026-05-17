package com.gestion.production.repositories;

import com.gestion.production.entities.MaintenancePiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MaintenancePieceRepository 
    extends JpaRepository<MaintenancePiece, Long> {

    // Trouve les pièces utilisées dans une maintenance
    List<MaintenancePiece> findByMaintenanceId(Long maintenanceId);

    // Trouve les maintenances qui ont utilisé une pièce
    List<MaintenancePiece> findByPieceId(Long pieceId);
}
package com.gestion.production.services;

import com.gestion.production.entities.MaintenancePiece;
import com.gestion.production.repositories.MaintenancePieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenancePieceService {

    @Autowired
    private MaintenancePieceRepository maintenancePieceRepository;

    public List<MaintenancePiece> getAllMaintenancePieces() {
        return maintenancePieceRepository.findAll();
    }

    public Optional<MaintenancePiece> getMaintenancePieceById(Long id) {
        return maintenancePieceRepository.findById(id);
    }

    public MaintenancePiece createMaintenancePiece(MaintenancePiece mp) {
        return maintenancePieceRepository.save(mp);
    }

    public MaintenancePiece updateMaintenancePiece(Long id, MaintenancePiece mp) {
        if (maintenancePieceRepository.existsById(id)) {
            mp.setId(id);
            return maintenancePieceRepository.save(mp);
        }
        return null;
    }

    public void deleteMaintenancePiece(Long id) {
        maintenancePieceRepository.deleteById(id);
    }

    // Récupère les pièces d'une maintenance
    public List<MaintenancePiece> getPiecesByMaintenance(Long maintenanceId) {
        return maintenancePieceRepository.findByMaintenanceId(maintenanceId);
    }
}
package com.gestion.production.services;

import com.gestion.production.entities.Maintenance;
import com.gestion.production.repositories.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    public List<Maintenance> getAllMaintenances() {
        return maintenanceRepository.findAll();
    }

    public Optional<Maintenance> getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id);
    }

    public Maintenance createMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    public Maintenance updateMaintenance(Long id, Maintenance maintenance) {
        if (maintenanceRepository.existsById(id)) {
            maintenance.setId(id);
            return maintenanceRepository.save(maintenance);
        }
        return null;
    }

    public void deleteMaintenance(Long id) {
        maintenanceRepository.deleteById(id);
    }

    // Récupère les maintenances par machine
    public List<Maintenance> getMaintenancesByMachine(Long machineId) {
        return maintenanceRepository.findByMachineId(machineId);
    }

    // Récupère les maintenances par statut
    public List<Maintenance> getMaintenancesByStatut(String statut) {
        return maintenanceRepository.findByStatut(statut);
    }

    // Récupère les maintenances entre deux dates
    public List<Maintenance> getMaintenancesByPeriode(
            LocalDate debut, LocalDate fin) {
        return maintenanceRepository.findByDateMaintenanceBetween(debut, fin);
    }

    // Récupère les maintenances d'aujourd'hui
    public List<Maintenance> getMaintenancesAujourdhui() {
        return maintenanceRepository.findMaintenancesAujourdhui();
    }
}
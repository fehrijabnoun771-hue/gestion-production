package com.gestion.production.repositories;

import com.gestion.production.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository 
    extends JpaRepository<Maintenance, Long> {

    // Trouve les maintenances par machine
    List<Maintenance> findByMachineId(Long machineId);

    // Trouve les maintenances par technicien
    List<Maintenance> findByTechnicienId(Long technicienId);

    // Trouve les maintenances par statut
    // ex: PLANIFIEE, EN_COURS, TERMINEE
    List<Maintenance> findByStatut(String statut);

    // Trouve les maintenances par type
    // ex: PREVENTIVE, CORRECTIVE
    List<Maintenance> findByType(String type);

    // Trouve les maintenances entre deux dates
    List<Maintenance> findByDateMaintenanceBetween(
        LocalDate debut, LocalDate fin);

    // Trouve les maintenances planifiées pour aujourd'hui
    @Query("SELECT m FROM Maintenance m WHERE m.dateMaintenance = CURRENT_DATE")
    List<Maintenance> findMaintenancesAujourdhui();
}
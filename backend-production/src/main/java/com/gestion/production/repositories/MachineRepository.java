package com.gestion.production.repositories;

import com.gestion.production.entities.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

// @Repository → dit à Spring que c'est un repository
// JpaRepository<Machine, Long> →
//   Machine = l'entité concernée
//   Long = type de la clé primaire (id)
@Repository
public interface MachineRepository 
    extends JpaRepository<Machine, Long> {

    // Spring génère automatiquement le SQL pour :
    // findAll()      → SELECT * FROM machine
    // findById(id)   → SELECT * FROM machine WHERE id = ?
    // save(machine)  → INSERT ou UPDATE
    // deleteById(id) → DELETE FROM machine WHERE id = ?

    // Méthode personnalisée → trouve les machines par état
    // Spring traduit automatiquement en :
    // SELECT * FROM machine WHERE etat = ?
    List<Machine> findByEtat(String etat);

    // Trouve les machines dont le nom contient un mot
    // SELECT * FROM machine WHERE nom LIKE '%keyword%'
    List<Machine> findByNomContaining(String keyword);

    // @Query → requête SQL personnalisée
    // Trouve les machines qui ont besoin de maintenance
    @Query("SELECT m FROM Machine m WHERE m.maintenanceProchaine <= CURRENT_DATE")
    List<Machine> findMachinesNeedingMaintenance();
}
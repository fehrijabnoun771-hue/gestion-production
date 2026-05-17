package com.gestion.production.repositories;

import com.gestion.production.entities.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TechnicienRepository 
    extends JpaRepository<Technicien, Long> {

    // Trouve les techniciens par nom
    List<Technicien> findByNomContaining(String nom);

    // Trouve les techniciens assignés à une machine
    // SELECT * FROM technicien WHERE machine_assignee_id = ?
    List<Technicien> findByMachineAssigneeId(Long machineId);

    // Trouve les techniciens non assignés à une machine
    @Query("SELECT t FROM Technicien t WHERE t.machineAssignee IS NULL")
    List<Technicien> findTechniciensDisponibles();

    // Trouve les techniciens par compétence
    // SELECT * FROM technicien WHERE competences LIKE '%competence%'
    List<Technicien> findByCompetencesContaining(String competence);
}
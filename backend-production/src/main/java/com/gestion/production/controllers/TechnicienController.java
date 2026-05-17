package com.gestion.production.controllers;

import com.gestion.production.entities.Technicien;
import com.gestion.production.services.TechnicienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/techniciens")
@CrossOrigin(origins = "http://localhost:4200")
public class TechnicienController {

    @Autowired
    private TechnicienService technicienService;

    @GetMapping
    public ResponseEntity<List<Technicien>> getAllTechniciens() {
        return ResponseEntity.ok(technicienService.getAllTechniciens());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Technicien> getTechnicienById(
            @PathVariable Long id) {
        return technicienService.getTechnicienById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Technicien> createTechnicien(
            @Valid @RequestBody Technicien technicien) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(technicienService.createTechnicien(technicien));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Technicien> updateTechnicien(
            @PathVariable Long id,
            @Valid @RequestBody Technicien technicien) {
        Technicien updated = technicienService.updateTechnicien(id, technicien);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnicien(@PathVariable Long id) {
        technicienService.deleteTechnicien(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Technicien>> getTechniciensDisponibles() {
        return ResponseEntity.ok(
            technicienService.getTechniciensDisponibles());
    }

    @GetMapping("/competence")
    public ResponseEntity<List<Technicien>> getTechniciensByCompetence(
            @RequestParam String competence) {
        return ResponseEntity.ok(
            technicienService.getTechniciensByCompetence(competence));
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Technicien>> getTechniciensByMachine(
            @PathVariable Long machineId) {
        return ResponseEntity.ok(
            technicienService.getTechniciensByMachine(machineId));
    }
}
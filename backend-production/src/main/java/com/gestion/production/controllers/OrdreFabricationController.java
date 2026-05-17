package com.gestion.production.controllers;

import com.gestion.production.entities.OrdreFabrication;
import com.gestion.production.services.OrdreFabricationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ordres")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdreFabricationController {

    @Autowired
    private OrdreFabricationService ordreFabricationService;

    @GetMapping
    public ResponseEntity<List<OrdreFabrication>> getAllOrdres() {
        return ResponseEntity.ok(ordreFabricationService.getAllOrdres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdreFabrication> getOrdreById(
            @PathVariable Long id) {
        return ordreFabricationService.getOrdreById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrdreFabrication> createOrdre(
            @Valid @RequestBody OrdreFabrication ordre) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ordreFabricationService.createOrdre(ordre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdreFabrication> updateOrdre(
            @PathVariable Long id,
            @Valid @RequestBody OrdreFabrication ordre) {
        OrdreFabrication updated = ordreFabricationService
            .updateOrdre(id, ordre);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdre(@PathVariable Long id) {
        ordreFabricationService.deleteOrdre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<OrdreFabrication>> getOrdresByStatut(
            @PathVariable String statut) {
        return ResponseEntity.ok(
            ordreFabricationService.getOrdresByStatut(statut));
    }

    @GetMapping("/urgents")
    public ResponseEntity<List<OrdreFabrication>> getOrdresUrgents() {
        return ResponseEntity.ok(ordreFabricationService.getOrdresUrgents());
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<OrdreFabrication>> getOrdresByMachine(
            @PathVariable Long machineId) {
        return ResponseEntity.ok(
            ordreFabricationService.getOrdresByMachine(machineId));
    }

    // PUT /api/ordres/1/statut?statut=EN_COURS
    @PutMapping("/{id}/statut")
    public ResponseEntity<OrdreFabrication> changeStatut(
            @PathVariable Long id,
            @RequestParam String statut) {
        OrdreFabrication updated = ordreFabricationService
            .changeStatut(id, statut);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }
}
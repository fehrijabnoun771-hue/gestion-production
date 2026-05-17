package com.gestion.production.controllers;

import com.gestion.production.entities.HistoriqueProduction;
import com.gestion.production.services.HistoriqueProductionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/historiques")
@CrossOrigin(origins = "http://localhost:4200")
public class HistoriqueProductionController {

    @Autowired
    private HistoriqueProductionService historiqueService;

    @GetMapping
    public ResponseEntity<List<HistoriqueProduction>> getAllHistoriques() {
        return ResponseEntity.ok(historiqueService.getAllHistoriques());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueProduction> getHistoriqueById(
            @PathVariable Long id) {
        return historiqueService.getHistoriqueById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HistoriqueProduction> createHistorique(
            @Valid @RequestBody HistoriqueProduction historique) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(historiqueService.createHistorique(historique));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorique(@PathVariable Long id) {
        historiqueService.deleteHistorique(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ordre/{ordreId}")
    public ResponseEntity<List<HistoriqueProduction>> getHistoriqueByOrdre(
            @PathVariable Long ordreId) {
        return ResponseEntity.ok(
            historiqueService.getHistoriqueByOrdre(ordreId));
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<HistoriqueProduction>> getHistoriqueByUtilisateur(
            @PathVariable Long utilisateurId) {
        return ResponseEntity.ok(
            historiqueService.getHistoriqueByUtilisateur(utilisateurId));
    }
}
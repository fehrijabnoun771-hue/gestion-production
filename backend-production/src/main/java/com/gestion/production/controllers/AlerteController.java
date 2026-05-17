package com.gestion.production.controllers;

import com.gestion.production.entities.Alerte;
import com.gestion.production.services.AlerteService;
import com.gestion.production.services.AlerteAutomatiqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alertes")
@CrossOrigin(origins = "http://localhost:4200")
public class AlerteController {

    @Autowired
    private AlerteService alerteService;

    @GetMapping
    public ResponseEntity<List<Alerte>> getAllAlertes() {
        return ResponseEntity.ok(alerteService.getAllAlertes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerte> getAlerteById(@PathVariable Long id) {
        return alerteService.getAlerteById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alerte> createAlerte(
            @Valid @RequestBody Alerte alerte) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(alerteService.createAlerte(alerte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerte> updateAlerte(
            @PathVariable Long id,
            @Valid @RequestBody Alerte alerte) {
        Alerte updated = alerteService.updateAlerte(id, alerte);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerte(@PathVariable Long id) {
        alerteService.deleteAlerte(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/non-lues")
    public ResponseEntity<List<Alerte>> getAlertesNonLues() {
        return ResponseEntity.ok(alerteService.getAlertesNonLues());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAlertesNonLues() {
        return ResponseEntity.ok(alerteService.countAlertesNonLues());
    }

    @PutMapping("/{id}/lire")
    public ResponseEntity<Alerte> marquerCommeLue(@PathVariable Long id) {
        Alerte updated = alerteService.marquerCommeLue(id);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @Autowired
    private AlerteAutomatiqueService alerteAutomatiqueService;
    // POST /api/alertes/verifier
    // // Déclenche la vérification manuelle des alertes
    // @PostMapping("/verifier")
    public ResponseEntity<String> verifierAlertes() {
        alerteAutomatiqueService.declencherAlertesManuellement();
        return ResponseEntity.ok("Vérification des alertes effectuée !");
    }



}
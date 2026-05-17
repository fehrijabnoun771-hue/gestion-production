package com.gestion.production.controllers;

import com.gestion.production.entities.Utilisateur;
import com.gestion.production.services.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(
            @PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(
            @Valid @RequestBody Utilisateur utilisateur) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(utilisateurService.createUtilisateur(utilisateur));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(
            @PathVariable Long id,
            @Valid @RequestBody Utilisateur utilisateur) {
        Utilisateur updated = utilisateurService
            .updateUtilisateur(id, utilisateur);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<Utilisateur>> getUtilisateursByRole(
            @PathVariable String role) {
        return ResponseEntity.ok(
            utilisateurService.getUtilisateursByRole(role));
    }
}
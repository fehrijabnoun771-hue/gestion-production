package com.gestion.production.controllers;

import com.gestion.production.entities.PieceRechange;
import com.gestion.production.services.PieceRechangeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pieces")
@CrossOrigin(origins = "http://localhost:4200")
public class PieceRechangeController {

    @Autowired
    private PieceRechangeService pieceRechangeService;

    @GetMapping
    public ResponseEntity<List<PieceRechange>> getAllPieces() {
        return ResponseEntity.ok(pieceRechangeService.getAllPieces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PieceRechange> getPieceById(@PathVariable Long id) {
        return pieceRechangeService.getPieceById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PieceRechange> createPiece(
            @Valid @RequestBody PieceRechange piece) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(pieceRechangeService.createPiece(piece));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PieceRechange> updatePiece(
            @PathVariable Long id,
            @Valid @RequestBody PieceRechange piece) {
        PieceRechange updated = pieceRechangeService.updatePiece(id, piece);
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePiece(@PathVariable Long id) {
        pieceRechangeService.deletePiece(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stock-faible")
    public ResponseEntity<List<PieceRechange>> getPiecesStockFaible() {
        return ResponseEntity.ok(pieceRechangeService.getPiecesStockFaible());
    }

    @GetMapping("/stock-epuise")
    public ResponseEntity<List<PieceRechange>> getPiecesStockEpuise() {
        return ResponseEntity.ok(pieceRechangeService.getPiecesStockEpuise());
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<PieceRechange> getPieceByReference(
            @PathVariable String reference) {
        return pieceRechangeService.getPieceByReference(reference)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
package com.gestion.production.services;

import com.gestion.production.entities.PieceRechange;
import com.gestion.production.repositories.PieceRechangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PieceRechangeService {

    @Autowired
    private PieceRechangeRepository pieceRechangeRepository;

    public List<PieceRechange> getAllPieces() {
        return pieceRechangeRepository.findAll();
    }

    public Optional<PieceRechange> getPieceById(Long id) {
        return pieceRechangeRepository.findById(id);
    }

    public PieceRechange createPiece(PieceRechange piece) {
        return pieceRechangeRepository.save(piece);
    }

    public PieceRechange updatePiece(Long id, PieceRechange piece) {
        if (pieceRechangeRepository.existsById(id)) {
            piece.setId(id);
            return pieceRechangeRepository.save(piece);
        }
        return null;
    }

    public void deletePiece(Long id) {
        pieceRechangeRepository.deleteById(id);
    }

    // Récupère les pièces avec stock faible
    public List<PieceRechange> getPiecesStockFaible() {
        return pieceRechangeRepository.findByStockLessThan(5);
    }

    // Récupère les pièces épuisées
    public List<PieceRechange> getPiecesStockEpuise() {
        return pieceRechangeRepository.findPiecesStockEpuise();
    }

    // Recherche par référence
    public Optional<PieceRechange> getPieceByReference(String reference) {
        return pieceRechangeRepository.findByReference(reference);
    }
}
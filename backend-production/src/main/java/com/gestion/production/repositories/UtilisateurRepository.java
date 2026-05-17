package com.gestion.production.repositories;

import com.gestion.production.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository 
    extends JpaRepository<Utilisateur, Long> {

    // Trouve un utilisateur par email
    // Utilisé pour l'authentification
    Optional<Utilisateur> findByEmail(String email);

    // Trouve les utilisateurs par rôle
    // SELECT * FROM utilisateur WHERE role = ?
    List<Utilisateur> findByRole(String role);

    // Trouve les utilisateurs actifs
    // SELECT * FROM utilisateur WHERE actif = true
    List<Utilisateur> findByActif(Boolean actif);

    // Vérifie si un email existe déjà
    // SELECT COUNT(*) FROM utilisateur WHERE email = ?
    boolean existsByEmail(String email);
}
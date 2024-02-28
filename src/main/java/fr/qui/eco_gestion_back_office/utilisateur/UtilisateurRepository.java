package fr.qui.eco_gestion_back_office.utilisateur;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>{
	// étends des méhodes de JpaRepository
	Optional<Utilisateur> findByEmail(String nom);
    boolean existsByEmail(String nom);
    Page<Utilisateur> findByIdIn(List<Long> ids, Pageable pageable);
}

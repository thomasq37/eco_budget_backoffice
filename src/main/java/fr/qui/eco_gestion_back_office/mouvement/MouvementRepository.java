package fr.qui.eco_gestion_back_office.mouvement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;

@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {

	List<Mouvement> findByUtilisateur(Utilisateur utilisateur);
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}

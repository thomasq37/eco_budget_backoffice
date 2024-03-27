package fr.qui.eco_gestion_back_office.compteur;

import fr.qui.eco_gestion_back_office.mouvement.Mouvement;
import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteurRepository extends JpaRepository<Compteur, Long> {
    List<Compteur> findByUtilisateur(Utilisateur utilisateur);

}


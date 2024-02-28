package fr.qui.eco_gestion_back_office.tag;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	
	List<Tag> findByMouvements_Utilisateur(Utilisateur utilisateur);

}

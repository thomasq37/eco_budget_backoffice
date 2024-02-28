package fr.qui.eco_gestion_back_office.tag;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.qui.eco_gestion_back_office.mouvement.MouvementRepository;
import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;
import fr.qui.eco_gestion_back_office.utilisateur.UtilisateurRepository;

@Service
public class TagService {
	
	 
    private TagRepository tagRepository;
    private final UtilisateurRepository utilisateurRepository;
    
    @Autowired
    public TagService(TagRepository tagRepository, UtilisateurRepository utilisateurRepository) {
    	this.tagRepository = tagRepository;
    	this.utilisateurRepository = utilisateurRepository;
    }

   

	public List<Tag> obtenirTags(String email) {
		Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return tagRepository.findByMouvements_Utilisateur(utilisateur);
	}

    // Méthodes du service pour gérer les opérations liées à Tag
}

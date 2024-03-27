package fr.qui.eco_gestion_back_office.mouvement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;
import fr.qui.eco_gestion_back_office.utilisateur.UtilisateurRepository;

@Service
public class MouvementService {

    
    private MouvementRepository mouvementRepository;
    private final UtilisateurRepository utilisateurRepository;
    
    @Autowired
    public MouvementService(MouvementRepository mouvementRepository, UtilisateurRepository utilisateurRepository) {
    	this.mouvementRepository = mouvementRepository;
    	this.utilisateurRepository = utilisateurRepository;
    }
    public List<Mouvement> obtenirMouvements(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return mouvementRepository.findByUtilisateur(utilisateur);
	}

    public List<Mouvement> ajouterMouvements(List<Mouvement> mouvements, String email) {
        // Trouver l'utilisateur pour s'assurer que les mouvements lui appartiennent
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        // Associer chaque mouvement à l'utilisateur et enregistrer
        mouvements.forEach(mouvement -> {
            mouvement.setUtilisateur(utilisateur); // Assurez-vous que votre entité Mouvement a un champ pour l'utilisateur et des setters appropriés
            mouvementRepository.save(mouvement);
        });

        return mouvements; // Retourner la liste des mouvements après les avoir enregistrés
    }

    public Mouvement ajouterMouvement(Mouvement mouvement, String email) {
        // Trouver l'utilisateur pour s'assurer que les mouvements lui appartiennent
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        mouvement.setUtilisateur(utilisateur); // Assurez-vous que votre entité Mouvement a un champ pour l'utilisateur et des setters appropriés
        return mouvementRepository.save(mouvement);
    }

    public Mouvement update(Long id, Mouvement mouvementDetails, String email) {
        // Trouver l'utilisateur pour s'assurer que les mouvements lui appartiennent
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        Mouvement mouvement = mouvementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mouvement non trouvé"));
        if(!utilisateur.getId().equals(mouvement.getUtilisateur().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé(e) à modifier ce mouvement.");
        }
        return mouvementRepository.findById(id)
                .map(existingMouvement -> {
                    existingMouvement.setType(mouvementDetails.getType());
                    existingMouvement.setNom(mouvementDetails.getNom());
                    existingMouvement.setMontant(mouvementDetails.getMontant());
                    existingMouvement.setFrequence(mouvementDetails.getFrequence());
                    return mouvementRepository.save(existingMouvement);
                })
                .orElseThrow(() -> new IllegalArgumentException("Mouvement not found with id " + id));
    }

    public void deleteById(Long id, String email) {
        // Trouver l'utilisateur pour s'assurer que les mouvements lui appartiennent
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        Mouvement mouvement = mouvementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mouvement non trouvé"));
        if(!utilisateur.getId().equals(mouvement.getUtilisateur().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé(e) à modifier ce mouvement.");
        }
        mouvementRepository.deleteById(id);
    }

    /*public Optional<Mouvement> findById(Long id) {
        return mouvementRepository.findById(id);
    }
    

    public Mouvement save(Mouvement mouvement) {
        return mouvementRepository.save(mouvement);
    }

    public void deleteById(Long id) {
        mouvementRepository.deleteById(id);
    }
*/
	
    


}
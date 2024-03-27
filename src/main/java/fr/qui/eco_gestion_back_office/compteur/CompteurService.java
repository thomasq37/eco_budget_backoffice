package fr.qui.eco_gestion_back_office.compteur;

import fr.qui.eco_gestion_back_office.mouvement.MouvementRepository;
import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;
import fr.qui.eco_gestion_back_office.utilisateur.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CompteurService {

    private final CompteurRepository compteurRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public CompteurService(CompteurRepository compteurRepository, UtilisateurRepository utilisateurRepository) {
        this.compteurRepository = compteurRepository;
        this.utilisateurRepository = utilisateurRepository;
    }
    public List<Compteur> obtenirCompteurs(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        return compteurRepository.findByUtilisateur(utilisateur);
    }

    /*public Optional<DepenseCompteur> obtenirUneDepenseCompteurParId(Long id) {
        return depenseCompteurRepository.findById(id);
    }

    public DepenseCompteur ajouterUneDepenseCompteur(DepenseCompteur depenseCompteur) {
        return depenseCompteurRepository.save(depenseCompteur);
    }

    public void supprimerUneDepenseCompteur(Long id) {
        depenseCompteurRepository.deleteById(id);
    }
*/}

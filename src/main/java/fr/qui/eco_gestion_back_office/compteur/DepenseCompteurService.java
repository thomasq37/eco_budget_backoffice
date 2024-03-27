package fr.qui.eco_gestion_back_office.compteur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepenseCompteurService {

    @Autowired
    private DepenseCompteurRepository depenseCompteurRepository;

    public List<DepenseCompteur> obtenirToutesLesDepenseCompteurs() {
        return depenseCompteurRepository.findAll();
    }

    public Optional<DepenseCompteur> obtenirUneDepenseCompteurParId(Long id) {
        return depenseCompteurRepository.findById(id);
    }

    public DepenseCompteur ajouterUneDepenseCompteur(DepenseCompteur depenseCompteur) {
        return depenseCompteurRepository.save(depenseCompteur);
    }

    public void supprimerUneDepenseCompteur(Long id) {
        depenseCompteurRepository.deleteById(id);
    }
}

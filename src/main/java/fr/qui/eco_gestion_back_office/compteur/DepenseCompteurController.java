package fr.qui.eco_gestion_back_office.compteur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depenseCompteurs")
@CrossOrigin(origins = "${app.cors.origin}")
public class DepenseCompteurController {

    @Autowired
    private DepenseCompteurService depenseCompteurService;

    @GetMapping
    public List<DepenseCompteur> obtenirToutesLesDepenseCompteurs() {
        return depenseCompteurService.obtenirToutesLesDepenseCompteurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepenseCompteur> obtenirUneDepenseCompteurParId(@PathVariable Long id) {
        return depenseCompteurService.obtenirUneDepenseCompteurParId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DepenseCompteur ajouterUneDepenseCompteur(@RequestBody DepenseCompteur depenseCompteur) {
        return depenseCompteurService.ajouterUneDepenseCompteur(depenseCompteur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepenseCompteur> majUneDepenseCompteur(@PathVariable Long id, @RequestBody DepenseCompteur depenseCompteurDetails) {
        return depenseCompteurService.obtenirUneDepenseCompteurParId(id)
                .map(depenseCompteur -> {
                    depenseCompteur.setMontant(depenseCompteurDetails.getMontant());
                    depenseCompteur.setDate(depenseCompteurDetails.getDate());
                    depenseCompteur.setCompteur(depenseCompteurDetails.getCompteur());
                    DepenseCompteur updatedDepenseCompteur = depenseCompteurService.ajouterUneDepenseCompteur(depenseCompteur);
                    return ResponseEntity.ok(updatedDepenseCompteur);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUneDepenseCompteur(@PathVariable Long id) {
        depenseCompteurService.supprimerUneDepenseCompteur(id);
        return ResponseEntity.ok().build();
    }
}

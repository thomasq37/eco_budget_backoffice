package fr.qui.eco_gestion_back_office.compteur;

import fr.qui.eco_gestion_back_office.mouvement.Mouvement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/compteurs")
@CrossOrigin(origins = "${app.cors.origin}")
public class CompteurController {

    @Autowired
    private CompteurService compteurService;

    @GetMapping
    public ResponseEntity<?> obtenirCompteurs() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        try {
            List<Compteur> compteurs = compteurService.obtenirCompteurs(email);
            return ResponseEntity.ok().body(compteurs);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    /*@GetMapping("/{id}")
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
    }*/
}

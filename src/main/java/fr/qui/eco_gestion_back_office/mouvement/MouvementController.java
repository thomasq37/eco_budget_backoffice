package fr.qui.eco_gestion_back_office.mouvement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/mouvements")
@CrossOrigin(origins = "${app.cors.origin}")
public class MouvementController {

    @Autowired
    private MouvementService mouvementService;

    @GetMapping()
    public ResponseEntity<?> obtenirMouvements() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); 
        try {
			List<Mouvement> mouvements = mouvementService.obtenirMouvements(email);
	        return ResponseEntity.ok().body(mouvements);

		} catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason()); 
		}
    }
    
    @PostMapping()
    public ResponseEntity<?> ajouterMouvements(@RequestBody List<Mouvement> mouvements) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); 
        try {
            // Ici, on ajoute les mouvements pour l'utilisateur spécifié par email
            // La logique exacte dépend de votre implémentation de service
            List<Mouvement> mouvementsAjoutes = mouvementService.ajouterMouvements(mouvements, email);
            return ResponseEntity.ok().body(mouvementsAjoutes);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PostMapping("/ajouter")
    public ResponseEntity<?> ajouterMouvement(@RequestBody Mouvement mouvement) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        try {
            // Ici, on ajoute les mouvements pour l'utilisateur spécifié par email
            // La logique exacte dépend de votre implémentation de service
            Mouvement mouvementAdded = mouvementService.ajouterMouvement(mouvement, email);
            return ResponseEntity.ok().body(mouvementAdded);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMouvement(@PathVariable Long id, @RequestBody Mouvement mouvementDetails) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        try {
            // Ici, on ajoute les mouvements pour l'utilisateur spécifié par email
            // La logique exacte dépend de votre implémentation de service
            Mouvement updatedMouvement = mouvementService.update(id, mouvementDetails, email);
            return ResponseEntity.ok().body(updatedMouvement);

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMouvement(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        try {
            mouvementService.deleteById(id, email);
            return ResponseEntity.ok().build();

        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }

    }

    /*@GetMapping("/{id}")
    public ResponseEntity<Mouvement> getMouvementById(@PathVariable Long id) {
        return mouvementService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }




  */
    
 
}

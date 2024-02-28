package fr.qui.eco_gestion_back_office.utilisateur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.qui.eco_gestion_back_office.tranche_budget.TrancheBudget;

@RestController
@RequestMapping("/api/utilisateur")
@CrossOrigin(origins = "${app.cors.origin}")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/profil")
    public ResponseEntity<Object> obtenirUtilisateur() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); 
        try {
			Utilisateur utilisateur = utilisateurService.obtenirUtilisateur(email);
	        return ResponseEntity.ok().body(utilisateur);

		} catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason()); 
		}
    }
    
    @PatchMapping("/modifier")
    public ResponseEntity<?> modifierUtilisateur(@RequestBody Utilisateur utilisateurModifie) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

         try {
			utilisateurService.modifierUtilisateur(email, utilisateurModifie);
	        return ResponseEntity.ok().body("Le compte a été mis à jour avec succès.");

		} catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason()); 
		}

    }

    @DeleteMapping("/supprimer")
    public ResponseEntity<?> supprimerCompte() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        utilisateurService.supprimerCompte(email);

        return ResponseEntity.ok().body("Le compte a été supprimé avec succès.");
    }

    @DeleteMapping("/admin/supprimer/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateurParAdmin(id);

        return ResponseEntity.ok().body("Le compte utilisateur a été supprimé avec succès.");
    }
    
    // Tranches utilisateurs
    
    @PatchMapping("/tranche/modifier/{trancheId}")
    public ResponseEntity<?> modifierMontantTranche(@PathVariable Long trancheId, @RequestBody Double nouveauMontant) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        try {
            TrancheBudget trancheModifiee = utilisateurService.modifierMontantTranche(email, trancheId, nouveauMontant);
            return ResponseEntity.ok(trancheModifiee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

}

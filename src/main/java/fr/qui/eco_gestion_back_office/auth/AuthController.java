package fr.qui.eco_gestion_back_office.auth;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.qui.eco_gestion_back_office.role.Role;
import fr.qui.eco_gestion_back_office.role.RoleRepository;
import fr.qui.eco_gestion_back_office.security.CustomUtilisateurDetailsService;
import fr.qui.eco_gestion_back_office.security.jwt.JwtUtils;
import fr.qui.eco_gestion_back_office.tranche_budget.TrancheBudget;
import fr.qui.eco_gestion_back_office.tranche_budget.TrancheBudgetService;
import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;
import fr.qui.eco_gestion_back_office.utilisateur.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CustomUtilisateurDetailsService customUtilisateurDetailsService;
    private final TentativeBlocageService tentativeBlocageService;
    private final TrancheBudgetService trancheBudgetService;

    public AuthController(UtilisateurRepository utilisateurRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils,
                          CustomUtilisateurDetailsService customUtilisateurDetailsService,
                          TentativeBlocageService tentativeBlocageService,
                          TrancheBudgetService trancheBudgetService) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.customUtilisateurDetailsService = customUtilisateurDetailsService;
        this.tentativeBlocageService = tentativeBlocageService;
        this.trancheBudgetService = trancheBudgetService;
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> registerUser(@RequestBody Utilisateur utilisateurRequest, HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            if (!tentativeBlocageService.peutTenterInscription(ip)) {
                return ResponseEntity.badRequest().body("Trop de tentatives d'inscription depuis cette adresse IP, veuillez réessayer plus tard.");
            }

            // Vérification de la longueur du mot de passe
            if (utilisateurRequest.getMdp().length() < 10) {
                return ResponseEntity.badRequest().body("Erreur: Le mot de passe doit contenir au moins 10 caractères.");
            }

            // Vérifier si le nom est déjà pris
            if (utilisateurRepository.existsByEmail(utilisateurRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Erreur: Ce mail est déjà pris!");
            }

            // Création et enregistrement de l'utilisateur
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(utilisateurRequest.getEmail());
            utilisateur.setMdp(passwordEncoder.encode(utilisateurRequest.getMdp()));

            // Obtenir le rôle par défaut (ROLE_USER)
            Role roleDefault = roleRepository.findByNom("ROLE_USER")
                    .orElseThrow(() -> new Exception("Rôle par défaut non trouvé"));

            // Créer l'ensemble des rôles
            Set<Role> roles = new HashSet<>();
            roles.add(roleDefault);

            // Attribuer les rôles à l'utilisateur
            utilisateur.setRoles(roles);
            
            Utilisateur utilisateurEnregistre = utilisateurRepository.save(utilisateur);


            // Créer et ajouter les tranches à l'utilisateur
            TrancheBudget trancheBudgetUne = new TrancheBudget("Bon", null, utilisateurEnregistre);
            trancheBudgetService.ajouterTrancheBudget(trancheBudgetUne);
            TrancheBudget trancheBudgetDeux = new TrancheBudget("Moyen", null, utilisateurEnregistre);
            trancheBudgetService.ajouterTrancheBudget(trancheBudgetDeux);
            TrancheBudget trancheBudgetTrois = new TrancheBudget("Mauvais", null, utilisateurEnregistre);
            trancheBudgetService.ajouterTrancheBudget(trancheBudgetTrois);

            // Enregistrer l'utilisateur avec les rôles et les tranches

            return ResponseEntity.ok("Utilisateur enregistré avec succès!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }


	@PostMapping("/connexion")
	public ResponseEntity<String> authenticateUser(@RequestBody Utilisateur loginRequest, HttpServletRequest request) {
        
		try {
			String ip = request.getRemoteAddr();
	        if (!tentativeBlocageService.peutTenterConnexion(ip)) {
	            return ResponseEntity.badRequest().body("Trop de tentatives de connexion depuis cette adresse IP, veuillez réessayer plus tard.");
	        }
			UserDetails userDetails = customUtilisateurDetailsService.loadUserByUsername(loginRequest.getEmail());
			if (passwordEncoder.matches(loginRequest.getMdp(), userDetails.getPassword())) {
				List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.toList(); 
				String jwt = jwtUtils.generateJwtToken(userDetails.getUsername(), roles);
				return ResponseEntity.ok(jwt);
			} else {
				return ResponseEntity.badRequest().body("Erreur: Email ou mot de passe incorrect!");
			}
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.badRequest().body("Erreur: Email ou mot de passe incorrect!");
		}
	}
	
}
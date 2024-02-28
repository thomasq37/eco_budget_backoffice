package fr.qui.eco_gestion_back_office.utilisateur;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import fr.qui.eco_gestion_back_office.mouvement.Mouvement;
import fr.qui.eco_gestion_back_office.role.Role;
import fr.qui.eco_gestion_back_office.tag.Tag;
import fr.qui.eco_gestion_back_office.tranche_budget.TrancheBudget;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Utilisateur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true) 
    private String email;
    private String mdp;  
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<TrancheBudget> tranchesBudget;
    
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mouvement> mouvements;
    
}

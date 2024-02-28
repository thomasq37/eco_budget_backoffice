package fr.qui.eco_gestion_back_office.tranche_budget;

import com.fasterxml.jackson.annotation.JsonBackReference;

import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class TrancheBudget {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	@Column(nullable = true)
	private Double montant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utilisateur_id")
	@JsonBackReference
	private Utilisateur utilisateur;

	// Constructeur par défaut requis par JPA
	public TrancheBudget() {
	}

	// Constructeur personnalisé
	public TrancheBudget(String nom, Double montant, Utilisateur utilisateur) {
		this.nom = nom;
		this.montant = montant;
		this.utilisateur = utilisateur;
	}
}

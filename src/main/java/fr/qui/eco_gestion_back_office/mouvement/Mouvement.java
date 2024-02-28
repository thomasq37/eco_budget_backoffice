package fr.qui.eco_gestion_back_office.mouvement;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.qui.eco_gestion_back_office.tag.Tag;
import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Mouvement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean type;
    private String nom;
    private Double montant;
    private Integer frequence;
    
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonIgnore
    private Utilisateur utilisateur; // Ajoutez cette ligne pour lier chaque mouvement à un utilisateur
    
    @Temporal(TemporalType.DATE)
    private Date dateModification; // Ajoutez cette ligne pour gérer la date de modification
    
    @Temporal(TemporalType.DATE)
    private Date dateCreation;
    
    @ManyToMany
    @JoinTable(
      name = "mouvement_tag", 
      joinColumns = @JoinColumn(name = "mouvement_id"), 
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}

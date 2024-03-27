package fr.qui.eco_gestion_back_office.compteur;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.qui.eco_gestion_back_office.utilisateur.Utilisateur;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Compteur
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonIgnore
    private Utilisateur utilisateur;
    @OneToMany(mappedBy = "compteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DepenseCompteur> depenses;
}

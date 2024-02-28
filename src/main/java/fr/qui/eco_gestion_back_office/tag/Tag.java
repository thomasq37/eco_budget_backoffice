package fr.qui.eco_gestion_back_office.tag;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.qui.eco_gestion_back_office.mouvement.Mouvement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
@Entity
@Data
public class Tag {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Mouvement> mouvements;

}

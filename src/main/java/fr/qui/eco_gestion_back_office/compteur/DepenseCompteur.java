package fr.qui.eco_gestion_back_office.compteur;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class DepenseCompteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "compteur_id", nullable = false)
    private Compteur compteur;
}


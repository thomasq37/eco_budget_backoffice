package fr.qui.eco_gestion_back_office.tranche_budget;

import org.springframework.stereotype.Service;

@Service
public class TrancheBudgetService {
	private final TrancheBudgetRepository trancheBudgetRepository;
	
	public TrancheBudgetService(TrancheBudgetRepository trancheBudgetRepository) {
		this.trancheBudgetRepository = trancheBudgetRepository;
	}
	// Ajouter un appartement si autorisé
	public TrancheBudget ajouterTrancheBudget(TrancheBudget trancheBudget) {
		return trancheBudgetRepository.save(trancheBudget);
	}
}

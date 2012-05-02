package profitbourse.modele;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.ArrayList;
import java.util.Iterator;


public class Portefeuille implements Serializable {

	private static final long serialVersionUID = -4302082586384377723L;
	private String nom;
	private ArrayList<Action> actions;
	private Currency devise;
	private Projet projet;
	
	public Portefeuille(String nom, Currency devise, Projet projet) {
		this.nom = nom;
		this.devise = devise;
		this.projet = projet;
		this.actions = new ArrayList<Action>();
	}
	
	public void ajouterNouvelleAction(Action action) {
		this.getActions().add(action);
	}
	
	public void supprimerTotalementAction(Action action) throws ActionNonPresenteDansLePortefeuille {
		boolean valide = this.getActions().remove(action);
		if (!valide) {
			throw new ActionNonPresenteDansLePortefeuille();
		}
	}
	
	public void majToutesLesActions() {
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			it.next().majWeb();
		}
	}
	
	public Money calculerTotalAchat() {
		Money somme = new Money(new BigDecimal(0.0), this.getDevise());
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			somme = somme.plus(it.next().calculerTotalAchat());
		}
		return somme;
	}
	
	public Money calculerTotalActuel() {
		Money somme = new Money(new BigDecimal(0.0), this.getDevise());
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			somme = somme.plus(it.next().calculerTotalActuel());
		}
		return somme;
	}
	
	public String portefeuilleEtActionsToString() {
		String affichage = this.toString();
		Iterator<Action> it = this.getActions().iterator();
		while (it.hasNext()) {
			affichage = affichage + "\n\t" + it.next().toString();
		}
		return affichage;
	}
	
	public String toString() {
		return "Portefeuille : '" + this.getNom() + "' en " + this.getDevise() + ", prix total à l'achat "
				+ this.calculerTotalAchat() + ", prix total actuel " + this.calculerTotalActuel() + ".";
	}
	
	public class ActionNonPresenteDansLePortefeuille extends Exception {
		private static final long serialVersionUID = -4261702974891478570L;
	}
	
	// GETTERS et SETTERS

	public ArrayList<Action> getActions() {
		return actions;
	}

	public void setActions(ArrayList<Action> actions) {
		this.actions = actions;
	}

	public String getNom() {
		return nom;
	}

	public Currency getDevise() {
		return devise;
	}

	public Projet getProjet() {
		return projet;
	}
	
}

package profitbourse.modele;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

public class Projet {
	
	private String nom;
	private File cheminSauvegarde;
	private HashSet<Portefeuille> portefeuilles;
	private HashSet<IndiceReference> indices;
	
	public Projet(String nom, File cheminSauvegarde) {
		this.nom = nom;
		this.cheminSauvegarde = cheminSauvegarde;
		this.portefeuilles = new HashSet<Portefeuille>();
		this.indices = new HashSet<IndiceReference>();
	}
	
	public void ajouterNouveauPortefeuille(Portefeuille portefeuille) {
		this.getPortefeuilles().add(portefeuille);
	}
	
	public void supprimerPortefeuille(Portefeuille portefeuille) throws PortefeuilleNonPresentDansLeProjet {
		boolean valide = this.getPortefeuilles().remove(portefeuille);
		if (!valide) {
			throw new PortefeuilleNonPresentDansLeProjet();
		}
	}
	
	public String projetEtPortefeuillesToString() {
		String affichage = this.toString();
		Iterator<Portefeuille> it = this.getPortefeuilles().iterator();
		while (it.hasNext()) {
			affichage = affichage + "\n\t" + it.next().toString();
		}
		return affichage;
	}
	
	public String toString() {
		return "Projet : '" + this.getNom() + "' enregistré à l'adresse : '" + this.getCheminSauvegarde() + "'.";
	}
	
	class PortefeuilleNonPresentDansLeProjet extends Exception {}

	// GETTERS et SETTERS
	
	public File getCheminSauvegarde() {
		return cheminSauvegarde;
	}

	public void setCheminSauvegarde(File cheminSauvegarde) {
		this.cheminSauvegarde = cheminSauvegarde;
	}

	public HashSet<Portefeuille> getPortefeuilles() {
		return portefeuilles;
	}

	public void setPortefeuilles(HashSet<Portefeuille> portefeuilles) {
		this.portefeuilles = portefeuilles;
	}

	public HashSet<IndiceReference> getIndices() {
		return indices;
	}

	public void setIndices(HashSet<IndiceReference> indices) {
		this.indices = indices;
	}

	public String getNom() {
		return nom;
	};

}

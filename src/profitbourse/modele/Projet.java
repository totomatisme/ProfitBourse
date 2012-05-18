package profitbourse.modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import profitbourse.modele.preferences.GestionnairePreferences;
import profitbourse.modele.sauvegarde.GestionnaireSauvegarde;

public class Projet implements Serializable {
	
	private static final long serialVersionUID = -2605790945339106666L;
	private String nom;
	private File cheminSauvegarde;
	private boolean modifie;
	private ArrayList<Portefeuille> portefeuilles;
	private ArrayList<Indice> indices;
	
	public Projet(String nom) {
		this.nom = nom;
		this.cheminSauvegarde = Projet.creerCheminDeSauvegardeParDefaut(nom);
		this.modifie = false;
		this.portefeuilles = new ArrayList<Portefeuille>();
		this.indices = new ArrayList<Indice>();
	}
	
	/**
	 * Permet de régler un problème dû à la sérialisation, pour réinitialiser les attributsts "transient".
	 */
	public void initialisationApresChargement() {
		// On initialise tous les portefeuilles de ce projet.
		Iterator<Portefeuille> it = this.getPortefeuilles().iterator();
		while (it.hasNext()) {
			it.next().initialisationApresChargement();
		}
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
	
	public void ajouterNouvelIndice(Indice indice) {
		this.getIndices().add(indice);
	}
	
	public void supprimerIndice(Indice indice) throws IndiceNonPresentDansLeProjet {
		boolean valide = this.getIndices().remove(indice);
		if (!valide) {
			throw new IndiceNonPresentDansLeProjet();
		}
	}
	
	public void majTousLesPortefeuillesEtIndices() {
		Iterator<Portefeuille> it = this.getPortefeuilles().iterator();
		while (it.hasNext()) {
			it.next().majToutesLesActions();
		}
		Iterator<Indice> iter = this.getIndices().iterator();
		while (iter.hasNext()) {
			iter.next().majWeb();
		}
	}
	
	public String projetEtPortefeuillesEtIndicesToString() {
		String affichage = this.toString();
		Iterator<Portefeuille> it = this.getPortefeuilles().iterator();
		while (it.hasNext()) {
			affichage = affichage + "\n\t" + it.next().toString();
		}
		affichage = affichage + "\n\t--------------------------";
		Iterator<Indice> iter = this.getIndices().iterator();
		while (iter.hasNext()) {
			affichage = affichage + "\n\t" + iter.next().toString();
		}
		return affichage;
	}
	
	public String toString() {
		return "Projet : '" + this.getNom() + "' enregistré à l'adresse : '" + this.getCheminSauvegarde() + "'.";
	}
	
	public void enregistrerProjet() throws FileNotFoundException, IOException {
		GestionnaireSauvegarde.enregistrerProjet(this, this.getCheminSauvegarde());
		GestionnairePreferences.setCheminSauvegarde(this.getCheminSauvegarde());
	}
	
	class PortefeuilleNonPresentDansLeProjet extends Exception {
		private static final long serialVersionUID = 3510456719637459437L;
	}
	
	class IndiceNonPresentDansLeProjet extends Exception {
		private static final long serialVersionUID = 3209724652088940744L;
	}

	// GETTERS et SETTERS
	
	public File getCheminSauvegarde() {
		return cheminSauvegarde;
	}

	public void setCheminSauvegarde(File cheminSauvegarde) {
		this.cheminSauvegarde = cheminSauvegarde;
	}

	public ArrayList<Portefeuille> getPortefeuilles() {
		return portefeuilles;
	}

	public void setPortefeuilles(ArrayList<Portefeuille> portefeuilles) {
		this.portefeuilles = portefeuilles;
	}

	public ArrayList<Indice> getIndices() {
		return indices;
	}

	public void setIndices(ArrayList<Indice> indices) {
		this.indices = indices;
	}

	public String getNom() {
		return nom;
	}

	public boolean isModifie() {
		return modifie;
	}

	public void setModifie(boolean modifie) {
		this.modifie = modifie;
	}
	
	public static File creerCheminDeSauvegardeParDefaut(String nomProjet) {
		return new File(GestionnairePreferences.getDossierSauvegarde().toString() + File.separator + nomProjet + ".pb");
	}

}

package profitbourse.modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import profitbourse.modele.majaleatoire.GestionnaireMajWeb;
import profitbourse.modele.preferences.GestionnairePreferences;
import profitbourse.modele.sauvegarde.GestionnaireSauvegarde;

public class Projet implements Serializable {
	
	private static final long serialVersionUID = -2605790945339106666L;
	private String nom;
	private File cheminSauvegarde;
	private boolean modifie;
	private ArrayList<Portefeuille> portefeuilles;
	private ArrayList<Indice> indices;
	private transient NotificationPortefeuilleAjoute notificationPortefeuilleAjoute;
	private transient NotificationPortefeuilleSupprime notificationPortefeuilleSupprime;
	private transient NotificationMajIndices notificationMajIndices;
	private transient NotificationIndiceAjoute notificationIndiceAjoute;
	private transient NotificationIndiceSupprime notificationIndiceSupprime;
	private transient NotificationModificationIndice notificationModificationIndice;
	private transient ObservateurModificationIndice observateurModificationIndice;
	
	public Projet(String nom) {
		this.nom = nom;
		this.cheminSauvegarde = Projet.creerCheminDeSauvegardeParDefaut(nom);
		this.modifie = false;
		this.portefeuilles = new ArrayList<Portefeuille>();
		this.indices = new ArrayList<Indice>();
		this.notificationPortefeuilleAjoute = new NotificationPortefeuilleAjoute();
		this.notificationPortefeuilleSupprime = new NotificationPortefeuilleSupprime();
		this.notificationMajIndices = new NotificationMajIndices();
		this.notificationIndiceAjoute = new NotificationIndiceAjoute();
		this.notificationIndiceSupprime = new NotificationIndiceSupprime();
		this.notificationModificationIndice = new NotificationModificationIndice();
		this.observateurModificationIndice = new ObservateurModificationIndice();
	}
	
	/**
	 * Permet de régler un problème dû à la sérialisation, pour réinitialiser les attributs "transient".
	 */
	public void initialisationApresChargement() {
		this.notificationPortefeuilleAjoute = new NotificationPortefeuilleAjoute();
		this.notificationPortefeuilleSupprime = new NotificationPortefeuilleSupprime();
		this.notificationMajIndices = new NotificationMajIndices();
		this.notificationIndiceAjoute = new NotificationIndiceAjoute();
		this.notificationIndiceSupprime = new NotificationIndiceSupprime();
		this.notificationModificationIndice = new NotificationModificationIndice();
		this.observateurModificationIndice = new ObservateurModificationIndice();
		
		// On initialise tous les portefeuilles de ce projet.
		Iterator<Portefeuille> it = this.getPortefeuilles().iterator();
		while (it.hasNext()) {
			it.next().initialisationApresChargement();
		}
		
		// On initialise tous les indices de ce projet.
		Iterator<Indice> iter = this.getIndices().iterator();
		while (iter.hasNext()) {
			iter.next().initialisationApresChargement();
		}
	}
	
	public void ajouterNouveauPortefeuille(Portefeuille portefeuille) {
		this.getPortefeuilles().add(portefeuille);
		int row = this.getPortefeuilles().size() - 1;
		this.notificationPortefeuilleAjoute.notifierPortefeuilleAjoute(portefeuille, row);
	}
	
	public void supprimerPortefeuille(Portefeuille portefeuille) throws PortefeuilleNonPresentDansLeProjet {
		int row = this.getPortefeuilles().indexOf(portefeuille);
		if (row == -1) {
			throw new PortefeuilleNonPresentDansLeProjet();
		} else {
			this.getPortefeuilles().remove(row);
			this.notificationPortefeuilleSupprime.notifierPortefeuilleSupprime(portefeuille, row);
		}
	}
	
	public void ajouterNouvelIndice(Indice indice) {
		this.getIndices().add(indice);
		int row = this.getIndices().size() - 1;
		this.notificationIndiceAjoute.notifierIndiceAjoute(indice, row);
	}
	
	public void supprimerIndice(Indice indice) throws IndiceNonPresentDansLeProjet {
		int row = this.getIndices().indexOf(indice);
		if (row == -1) {
			throw new IndiceNonPresentDansLeProjet();
		} else {
			this.getIndices().remove(row);
			this.notificationIndiceSupprime.notifierIndiceSupprime(indice, row);
		}
	}
	
	public void majTousLesPortefeuillesEtIndices() {
		Iterator<Portefeuille> it = this.getPortefeuilles().iterator();
		while (it.hasNext()) {
			it.next().majToutesLesActions();
		}
		Iterator<Indice> iter = this.getIndices().iterator();
		while (iter.hasNext()) {
			GestionnaireMajWeb.majIndice(iter.next());
		}
		this.notificationMajIndices.notifierMajIndices();
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
		//return "Projet : '" + this.getNom() + "' enregistré à l'adresse : '" + this.getCheminSauvegarde() + "'.";
		return this.getNom();
	}
	
	public class PortefeuilleNonPresentDansLeProjet extends Exception {
		private static final long serialVersionUID = 3510456719637459437L;
	}
	
	public class IndiceNonPresentDansLeProjet extends Exception {
		private static final long serialVersionUID = 3209724652088940744L;
	}
	
	public class NotificationPortefeuilleAjoute extends Observable {
		private int row = 0;
		
		public void notifierPortefeuilleAjoute(Portefeuille portefeuilleAjoute, int row) {
			this.row = row;
			this.setChanged();
			this.notifyObservers(portefeuilleAjoute);
		}

		public int getRow() {
			return row;
		}
	}
	
	public class NotificationPortefeuilleSupprime extends Observable {
		private int row = 0;
		
		public void notifierPortefeuilleSupprime(Portefeuille portefeuilleSupprime, int row) {
			this.row = row;
			this.setChanged();
			this.notifyObservers(portefeuilleSupprime);
		}

		public int getRow() {
			return row;
		}
	}
	
	public class NotificationMajIndices extends Observable {
		public void notifierMajIndices() {
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	public class NotificationIndiceAjoute extends Observable {
		private int row = 0;
		
		public void notifierIndiceAjoute(Indice indiceAjoute, int row) {
			this.row = row;
			this.setChanged();
			this.notifyObservers(indiceAjoute);
		}

		public int getRow() {
			return row;
		}
	}
	
	public class NotificationIndiceSupprime extends Observable {
		private int row = 0;
		
		public void notifierIndiceSupprime(Indice indiceSupprime, int row) {
			this.row = row;
			this.setChanged();
			this.notifyObservers(indiceSupprime);
		}

		public int getRow() {
			return row;
		}
	}
	
	public class NotificationModificationIndice extends Observable {
		private int row = 0;
		
		public void notifierModificationIndice(Indice indice, int row) {
			this.row = row;
			this.setChanged();
			this.notifyObservers(indice);
		}
		
		public int getRow() {
			return row;
		}
	}
	
	private class ObservateurModificationIndice implements Observer {
		public void update(Observable arg0, Object arg1) {
			Indice indiceModifie = (Indice)arg1;
			int index = indices.indexOf(indiceModifie);
			if (index != -1) {
				notificationModificationIndice.notifierModificationIndice(indiceModifie, index);
			}
		}
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

	public NotificationPortefeuilleAjoute getNotificationPortefeuilleAjoute() {
		return notificationPortefeuilleAjoute;
	}

	public NotificationPortefeuilleSupprime getNotificationPortefeuilleSupprime() {
		return notificationPortefeuilleSupprime;
	}

	public NotificationMajIndices getNotificationMajIndices() {
		return notificationMajIndices;
	}

	public NotificationIndiceAjoute getNotificationIndiceAjoute() {
		return notificationIndiceAjoute;
	}

	public NotificationIndiceSupprime getNotificationIndiceSupprime() {
		return notificationIndiceSupprime;
	}

	public NotificationModificationIndice getNotificationModificationIndice() {
		return notificationModificationIndice;
	}

	public ObservateurModificationIndice getObservateurModificationIndice() {
		return observateurModificationIndice;
	}

}

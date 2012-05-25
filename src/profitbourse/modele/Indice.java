package profitbourse.modele;

import java.io.Serializable;
import java.util.Observable;

import profitbourse.modele.majaleatoire.GestionnaireMajWeb;

public class Indice implements Serializable {

	private static final long serialVersionUID = -3456702227825000255L;
	private String nom;
	private String code;
	private ValeurCoursPoints cours;
	private float variation;
	private Projet projet;
	private transient NotificationModificationIndice notificationModificationIndice;
	
	public Indice(String nom, String code, Projet projet) {
		this.nom = nom;
		this.code = code;
		this.projet = projet;
		this.cours = null;
		this.variation = 0;
		this.notificationModificationIndice = new NotificationModificationIndice();
		this.notificationModificationIndice.addObserver(this.projet.getObservateurModificationIndice());
	}
	
	/**
	 * Permet de régler un problème dû à la sérialisation, pour réinitialiser les attributs "transient".
	 */
	public void initialisationApresChargement() {
		this.notificationModificationIndice = new NotificationModificationIndice();
		this.notificationModificationIndice.addObserver(this.projet.getObservateurModificationIndice());
	}
	
	public void majWeb() {
		GestionnaireMajWeb.majIndice(this);
		this.notificationModificationIndice.notifierModificationIndice(this);
	}
	
	public void majCoursEtVariation(ValeurCoursPoints nouveauCours, float nouvelleVariation) {
		this.cours = nouveauCours;
		this.variation = nouvelleVariation;
	}
	
	public String toString() {
		/*return "Indice : '" + this.getNom() + "', '" + this.getCode() + "', cours : " + this.getCours() 
				+ " pts (variation " + this.getVariation() + ").";*/
		return this.getNom();
	}
	
	public class NotificationModificationIndice extends Observable {
		public void notifierModificationIndice(Indice indice) {
			this.setChanged();
			this.notifyObservers(indice);
		}
	}

	// GETTERS et SETTERS
	
	public ValeurCoursPoints getCours() {
		return cours;
	}

	public float getVariation() {
		return variation;
	}

	public String getNom() {
		return nom;
	}

	public String getCode() {
		return code;
	}

	public Projet getProjet() {
		return projet;
	}

	public NotificationModificationIndice getNotificationModificationIndice() {
		return notificationModificationIndice;
	}
	
}

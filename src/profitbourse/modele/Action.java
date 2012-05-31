package profitbourse.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

import profitbourse.modele.majthomas.GestionnaireMajWeb;


public class Action implements Serializable {

	private static final long serialVersionUID = -7290657013980972234L;
	private String nom;
	private String codeISIN;
	private int quantite;
	private Money coursAchat;
	private Money coursActuel;
	private Date dateAchat;
	private VariationPourcent variation;
	private Portefeuille portefeuille;
	private transient NotificationModificationAction notificationModificationAction;
	
	public Action(String nom, String codeISIN, int quantite, Portefeuille portefeuille) {
		this.nom = nom;
		this.codeISIN = codeISIN;
		this.quantite = quantite;
		this.portefeuille = portefeuille;
		this.dateAchat = new Date();
		this.coursAchat = null;
		this.coursActuel = null;
		this.variation = null;
		this.notificationModificationAction = new NotificationModificationAction();
		this.notificationModificationAction.addObserver(this.portefeuille.getObservateurModificationAction());
	}
	
	/**
	 * Permet de régler un problème dû à la sérialisation, pour réinitialiser les attributs "transient".
	 */
	public void initialisationApresChargement() {
		this.notificationModificationAction = new NotificationModificationAction();
		this.notificationModificationAction.addObserver(this.portefeuille.getObservateurModificationAction());
	}
	
	public void premiereMajWeb() {
		ArrayList<Action> arrayList = new ArrayList<Action>();
		arrayList.add(this);
		GestionnaireMajWeb.majActions(arrayList);
		this.coursAchat = (Money)this.getCoursActuel().clone();
		this.notificationModificationAction.notifierModificationAction(this);
	}
	
	public void majWeb() {
		ArrayList<Action> arrayList = new ArrayList<Action>();
		arrayList.add(this);
		GestionnaireMajWeb.majActions(arrayList);
		this.notificationModificationAction.notifierModificationAction(this);
	}
	
	public void vendreEnPartieAction(int quantiteVendue) {
		this.quantite = this.getQuantite() - quantiteVendue;
		this.notificationModificationAction.notifierModificationAction(this);
	}
	
	public void majCoursEtVariation(Money nouveauCours, VariationPourcent nouvelleVariation) {
		this.coursActuel = nouveauCours;
		this.variation = nouvelleVariation;
	}

	public Money calculerTotalAchat() {
		return this.getCoursAchat().times(this.getQuantite());
	}
	
	public Money calculerTotalActuel() {
		return this.getCoursActuel().times(this.getQuantite());
	}
	
	public Money calculerPlusValue() {
		return this.calculerTotalActuel().minus(this.calculerTotalAchat());
	}
	
	public String toString() {
		/*return "Action : '" + this.getNom() + "', " + this.getCodeISIN() + ", " + this.getQuantite() 
				+ " achetés le " + DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(this.getDateAchat()) + " pour un cours de " + this.getCoursAchat() 
				+ ", au total " + this.calculerTotalAchat() + ". Actuellement vaut " + this.getCoursActuel() 
				+ " (variation " + this.getVariation() + "), au total " + this.calculerTotalActuel() + ".";*/
		return this.getNom();
	}
	
	public class NotificationModificationAction extends Observable {
		public void notifierModificationAction(Action action) {
			this.setChanged();
			this.notifyObservers(action);
		}
	}
	
	// GETTERS et SETTERS

	public int getQuantite() {
		return quantite;
	}

	public Money getCoursActuel() {
		return coursActuel;
	}

	public VariationPourcent getVariation() {
		return variation;
	}

	public String getNom() {
		return nom;
	}

	public String getCodeISIN() {
		return codeISIN;
	}

	public Date getDateAchat() {
		return dateAchat;
	}
	
	public Portefeuille getPortefeuille() {
		return portefeuille;
	}

	public Money getCoursAchat() {
		return coursAchat;
	}

	public NotificationModificationAction getNotificationModificationAction() {
		return notificationModificationAction;
	}
	
}

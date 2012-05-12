package profitbourse.modele;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import profitbourse.modele.majaleatoire.GestionnaireMajWeb;


public class Action implements Serializable {

	private static final long serialVersionUID = -7290657013980972234L;
	private String nom;
	private String codeISIN;
	private int quantite;
	private Money coursAchat;
	private Money coursActuel;
	private Date dateAchat;
	private float variation;
	private Portefeuille portefeuille;
	
	public Action(String nom, String codeISIN, int quantite, Portefeuille portefeuille) {
		this.nom = nom;
		this.codeISIN = codeISIN;
		this.quantite = quantite;
		this.portefeuille = portefeuille;
		this.dateAchat = new Date();
		this.coursAchat = null;
		this.coursActuel = null;
		this.variation = 0;
	}
	
	public void premiereMajWeb() {
		GestionnaireMajWeb.majAction(this);
		this.setCoursAchat(this.getCoursActuel());
	}
	
	public void majWeb() {
		GestionnaireMajWeb.majAction(this);
	}
	
	public void vendreEnPartieAction(int quantiteVendue) {
		this.setQuantite(this.getQuantite() - quantiteVendue);
	}

	public Money calculerTotalAchat() {
		return this.getCoursAchat().times(this.getQuantite());
	}
	
	public Money calculerTotalActuel() {
		return this.getCoursActuel().times(this.getQuantite());
	}
	
	public Money calculerPlusValue() {
		return this.getCoursActuel().minus(this.getCoursAchat());
	}
	
	public String toString() {
		return "Action : '" + this.getNom() + "', " + this.getCodeISIN() + ", " + this.getQuantite() 
				+ " achetés le " + DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(this.getDateAchat()) + " pour un cours de " + this.getCoursAchat() 
				+ ", au total " + this.calculerTotalAchat() + ". Actuellement vaut " + this.getCoursActuel() 
				+ " (variation " + this.getVariation() + "), au total " + this.calculerTotalActuel() + ".";
	}
	
	// GETTERS et SETTERS

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Money getCoursActuel() {
		return coursActuel;
	}

	public void setCoursActuel(Money cours) {
		this.coursActuel = cours;
	}

	public float getVariation() {
		return variation;
	}

	public void setVariation(float variation) {
		this.variation = variation;
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

	private void setCoursAchat(Money coursAchat) {
		this.coursAchat = coursAchat;
	}
	
}

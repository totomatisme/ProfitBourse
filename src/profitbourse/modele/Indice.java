package profitbourse.modele;

import java.io.Serializable;

import profitbourse.modele.majweb.GestionnaireMajWeb;

public class Indice implements Serializable {

	private static final long serialVersionUID = -3456702227825000255L;
	private String nom;
	private String code;
	private float cours;
	private float variation;
	private Projet projet;
	
	public Indice(String nom, String code, Projet projet) {
		this.nom = nom;
		this.code = code;
		this.projet = projet;
		this.cours = 0;
		this.variation = 0;
	}
	
	public void majWeb() {
		GestionnaireMajWeb.majIndice(this);
	}
	
	public String toString() {
		return "Indice : '" + this.getNom() + "', '" + this.getCode() + "', cours : " + this.getCours() 
				+ " pts (variation " + this.getVariation() + ").";
	}

	// GETTERS et SETTERS
	
	public float getCours() {
		return cours;
	}

	public void setCours(float cours) {
		this.cours = cours;
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

	public String getCode() {
		return code;
	}

	public Projet getProjet() {
		return projet;
	}
	
}

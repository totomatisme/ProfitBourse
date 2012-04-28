package profitbourse.modele;

import profitbourse.modele.majweb.GestionnaireMajWeb;

public class IndiceReference {

	private String nom;
	private String code;
	private float cours;
	private float variation;
	private Projet projet;
	
	public IndiceReference(String nom, String code, Projet projet) {
		this.nom = nom;
		this.code = code;
		this.projet = projet;
		this.cours = 0;
		this.variation = 0;
	}
	
	public void majWeb() {
		GestionnaireMajWeb.majIndice(this);
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

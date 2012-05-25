package profitbourse.modele;

import java.io.Serializable;
import java.text.DecimalFormat;

public class VariationPourcent implements Serializable, Cloneable {

	private static final long serialVersionUID = 5944993674320766444L;
	
	private static DecimalFormat formatDecimal;
	static {
		formatDecimal = new DecimalFormat("0.00");
	}
	
	private double valeur;
	private boolean positif;
	private boolean zero;
	
	public VariationPourcent(double valeur) {
		this.setValeur(valeur);
	}
	
	public String toString() {
		String resultat = formatDecimal.format(this.getValeur()) + "%";
		if (this.isPositif()) {
			return "+" + resultat;
		} else {
			return resultat;
		}
	}
	
	protected Object clone() {
		return new VariationPourcent(this.getValeur());
	}
	
	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
		if (this.valeur == 0) {
			this.zero = true;
		} else {
			this.zero = false;
			if (this.valeur < 0) {
				this.positif = false;
			} else {
				this.positif = true;
			}
		}
	}

	public boolean isPositif() {
		return positif;
	}

	public boolean isZero() {
		return zero;
	}
	
	/*
	public static void main(String[] args) {
		VariationPourcent v = new VariationPourcent(.09);
		System.out.println(v.toString());
	}
	*/
}

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
	
	public VariationPourcent(String variationPourcent) throws NumberFormatException {
		this.parseVariationPourcent(variationPourcent);
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
	
	public void parseVariationPourcent(String variationPourcent) throws NumberFormatException {
		// parse une chaine du type "+9.33%"
		String decimalSeulement = variationPourcent.substring(0, variationPourcent.length()-1);
		this.setValeur(Double.parseDouble(decimalSeulement));
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
		VariationPourcent v = new VariationPourcent("+65.8%");
		System.out.println(v.toString());
	}
	*/
}

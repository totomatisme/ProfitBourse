package profitbourse.modele;

import java.io.Serializable;
import java.text.DecimalFormat;

public class VariationPourcent implements Serializable {

	private static final long serialVersionUID = 5944993674320766444L;
	
	private static DecimalFormat formatDecimal;
	static {
		formatDecimal = new DecimalFormat("#.00");
	}
	
	private double valeur;
	private boolean positif;
	
	public VariationPourcent(double valeur) {
		this.setValeur(valeur);
	}
	
	public String toString() {
		String resultat = formatDecimal.format(this.getValeur()) + "%";
		if (this.isPositif()) {
			return "+" + resultat;
		} else {
			return "-" + resultat;
		}
	}
	
	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
		if (this.valeur < 0) {
			this.positif = false;
		} else {
			this.positif = true;
		}
	}

	public boolean isPositif() {
		return positif;
	}
	
	/*
	public static void main(String[] args) {
		VariationPourcent v = new VariationPourcent(09.09);
		System.out.println(v.toString());
	}
	*/
}

package profitbourse.modele;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ValeurCoursPoints implements Serializable, Cloneable {

	private static final long serialVersionUID = 5203267473810739983L;
	private static DecimalFormat formatDecimal;
	static {
		formatDecimal = new DecimalFormat("0.00");
	}
	
	private double valeur;
	
	public ValeurCoursPoints(double valeur) {
		this.setValeur(valeur);
	}
	
	public String toString() {
		return formatDecimal.format(this.getValeur()) + " pts";
	}
	
	protected Object clone() {
		return new ValeurCoursPoints(this.getValeur());
	}

	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
	
	/*
	public static void main(String[] args) {
		ValeurCoursPoints v = new ValeurCoursPoints(100909.09998);
		System.out.println(v.toString());
	}
	*/
	
}

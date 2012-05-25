package profitbourse.modele.majaleatoire;

import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;

import profitbourse.modele.*;

public class GestionnaireMajWeb {
	
	public static void majAction(Action action) {
		BigDecimal nouveauCoursBigDecimal = new BigDecimal(Math.random()*100.0);
		nouveauCoursBigDecimal = nouveauCoursBigDecimal.setScale(2, RoundingMode.HALF_EVEN);
		Money nouveauCours = new Money(nouveauCoursBigDecimal, action.getPortefeuille().getDevise());
		Double nouvelleVariationDouble = new Double(Math.random()*10.0 - 5.0);
		action.majCoursEtVariation(nouveauCours, new VariationPourcent(nouvelleVariationDouble));
	}
	
	public static void majIndice(Indice indice) {
		Double nouveauCoursDouble = new Double(Math.random()*10000.0);
		Double nouvelleVariationDouble = new Double(Math.random()*10.0 - 5.0);
		indice.majCoursEtVariation(new ValeurCoursPoints(nouveauCoursDouble), new VariationPourcent(nouvelleVariationDouble));
	}
	
	public static boolean testExistenceCodeISIN(String codeATester) {
		return true;
	}
	
}

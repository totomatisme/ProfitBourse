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
		action.majCoursEtVariation(nouveauCours, nouvelleVariationDouble.floatValue());
	}
	
	public static void majIndice(Indice indice) {
		Double nouveauCoursDouble = new Double(Math.random()*10000.0);
		Double nouvelleVariationDouble = new Double(Math.random()*10.0 - 5.0);
		indice.majCoursEtVariation(nouveauCoursDouble.floatValue(), nouvelleVariationDouble.floatValue());
	}
	
	public static boolean testExistenceCodeISIN(String codeATester) {
		return true;
	}
	
}

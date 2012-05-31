package profitbourse.modele.majweb;

import profitbourse.modele.Action;
import profitbourse.modele.Portefeuille;

import java.util.Iterator;

public class AddressGeneratorActions {
	
	public static String toString(Portefeuille portefeuille) {
		return "http://download.finance.yahoo.com/d/quotes.csv?s=" + AddressGeneratorActions.getActions(portefeuille) + "&f="+ AddressGeneratorActions.getTag();
	}

	public static String getActions(Portefeuille portefeuille){
		String list = null;
		Iterator<Action> it = portefeuille.getActions().iterator();
		while (it.hasNext()) {
			list = list + "+" + it.next().getCodeISIN();
		}
		return list;
	
	}
	
	public static String getTag(){
		String tag = "sl1p2d1t1";
		return tag;//s=symbol l1=Last trade d1=last trade date t1=last trade time
		// ajouter variation en 3e position
	}

}

package profitbourse.modele.majweb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVParser;

import profitbourse.modele.Action;
import profitbourse.modele.Money;
import profitbourse.modele.VariationPourcent;

public class MiseAJourActions extends AbstractMiseAJour {
	
	private static final String TAG_MAJ_ACTIONS = "sl1p2d1t1";//s=symbol l1=Last trade p2=change in percent d1=last trade date t1=last trade time

	public MiseAJourActions(ArrayList<Object> arrayList) {
		super(arrayList);
	}

	protected URL creerURL() throws MalformedURLException {
		String liste = "";
		Iterator<Object> it = this.arrayList.iterator();
		while (it.hasNext()) {
			liste = liste + "+" + ((Action)it.next()).getCodeISIN();
		}
		return new URL("http://download.finance.yahoo.com/d/quotes.csv?s=" + liste + "&f=" + TAG_MAJ_ACTIONS + "&e=.csv");
	}

	protected void parserLigne(String ligne, Object actionOuIndice) throws CodeNeCorrespondPasAuCodeDeYahoo, NumberFormatException, EchecDuParser {
		Action actionTraitee = (Action)actionOuIndice;
		
		CSVParser parser = new CSVParser(',', '"');
		String[] contenu = null;
		

		String code = null;
		String cours = null;
		String variation = null;
		
		try {
			contenu = parser.parseLine(ligne);
			
			code = contenu[0];
			cours = contenu[1];
			variation = contenu[2];
		} catch (Exception e) {
			throw new EchecDuParser();
		}
		
		// Si le code n'est pas bon c'est qu'il y a un serieux problème !
		if (!actionTraitee.getCodeISIN().equals(code)) {
			throw new CodeNeCorrespondPasAuCodeDeYahoo();
		}
		
		Money coursMoney = new Money(new BigDecimal(cours).setScale(2,RoundingMode.HALF_EVEN), actionTraitee.getPortefeuille().getDevise());
		VariationPourcent variationPourcent = new VariationPourcent(variation);
		
		actionTraitee.majCoursEtVariation(coursMoney, variationPourcent);
	}

}

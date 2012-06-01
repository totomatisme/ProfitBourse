package profitbourse.modele.majweb;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVParser;

import profitbourse.modele.Indice;
import profitbourse.modele.ValeurCoursPoints;
import profitbourse.modele.VariationPourcent;

public class MiseAJourIndices extends AbstractMiseAJour {

	private static final String TAG_MAJ_INDICES = "sl1p2d1t1";//s=symbol l1=Last trade p2=change in percent d1=last trade date t1=last trade time
	
	public MiseAJourIndices(ArrayList<Object> arrayList) {
		super(arrayList);
	}

	protected URL creerURL() throws MalformedURLException {
		String liste = "";
		Iterator<Object> it = this.arrayList.iterator();
		while (it.hasNext()) {
			liste = liste + "+" + ((Indice)it.next()).getCode();
		}
		return new URL("http://download.finance.yahoo.com/d/quotes.csv?s=" + liste + "&f=" + TAG_MAJ_INDICES + "&e=.csv");
	}

	protected void parserLigne(String ligne, Object actionOuIndice) throws CodeNeCorrespondPasAuCodeDeYahoo, NumberFormatException, EchecDuParser {
		Indice indiceTraite = (Indice)actionOuIndice;
		
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
		if (!indiceTraite.getCode().equals(code)) {
			throw new CodeNeCorrespondPasAuCodeDeYahoo();
		}
		
		ValeurCoursPoints valeurCoursPoints = new ValeurCoursPoints(cours);
		VariationPourcent variationPourcent = new VariationPourcent(variation);
		
		indiceTraite.majCoursEtVariation(valeurCoursPoints, variationPourcent);
	}

}

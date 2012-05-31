package profitbourse.modele.majthomas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import au.com.bytecode.opencsv.CSVParser;

import profitbourse.modele.majthomas.CodeNeCorrespondPasAuCodeDeYahoo;
import profitbourse.modele.majthomas.EchecDuParser;
import profitbourse.modele.majthomas.ProblemeChargementPageWeb;

public class TrouverNomPourLeCode {

	private static final String TAG_NOM_POUR_CODE = "sn";//s=symbol n=name
	
	private String code;
	
	public TrouverNomPourLeCode(String code) {
		this.code = code;
	}
	
	public String recupererLeNomPourLeCode() throws MalformedURLException, ProblemeChargementPageWeb, EchecDuParser, CodeNeCorrespondPasAuCodeDeYahoo {
		// On crée l'URL qui va nous permettre d'interroger les serveurs de Yahoo
		URL url = this.creerURL();
		
		// On récupère la page Web et on la traite
		String nom = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			System.out.println("Nouvelle page yahoo demandée :");
			String inputLine = in.readLine();
			nom = this.parserLigne(inputLine);
			System.out.println(inputLine);
			System.out.println("Fin de la page.");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ProblemeChargementPageWeb();
		}
		
		return nom;
	}
	
	protected String parserLigne(String ligne) throws EchecDuParser, CodeNeCorrespondPasAuCodeDeYahoo {
		CSVParser parser = new CSVParser(',', '"');
		String[] contenu = null;
		
		try {
			contenu = parser.parseLine(ligne);
		} catch (Exception e) {
			throw new EchecDuParser();
		}
		
		String codeObtenu = contenu[0];
		String nomObtenu = contenu[1];
		
		// Si le code n'est pas bon c'est qu'il y a un serieux problème !
		if (!this.code.equals(codeObtenu)) {
			throw new CodeNeCorrespondPasAuCodeDeYahoo();
		}
		
		return nomObtenu;
	}
	
	private URL creerURL() throws MalformedURLException {
		return new URL("http://download.finance.yahoo.com/d/quotes.csv?s=" + this.code + "&f=" + TAG_NOM_POUR_CODE + "&e=.csv");
	}
	
}

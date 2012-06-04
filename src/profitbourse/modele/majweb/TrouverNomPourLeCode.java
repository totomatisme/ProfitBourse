package profitbourse.modele.majweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import au.com.bytecode.opencsv.CSVParser;

import profitbourse.modele.majweb.CodeNeCorrespondPasAuCodeDeYahoo;
import profitbourse.modele.majweb.EchecDuParser;
import profitbourse.modele.majweb.ProblemeChargementPageWeb;

public class TrouverNomPourLeCode {

	private static final String TAG_NOM_POUR_CODE = "sni";//s=symbol n=name i=info
	
	private String code;
	
	public TrouverNomPourLeCode(String code) {
		this.code = code;
	}
	
	public String recupererLeNomPourLeCode() throws MalformedURLException, ProblemeChargementPageWeb, EchecDuParser, CodeNeCorrespondPasAuCodeDeYahoo, ErreurCodeInconnu {
		try {
			// On crée l'URL qui va nous permettre d'interroger les serveurs de Yahoo
			URL url = this.creerURL();
		
			// On récupère la page Web et on la traite
			String nom = null;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			System.out.println("Nouvelle page yahoo demandée : " + url.toString());
			String inputLine = in.readLine();
			nom = this.parserLigne(inputLine);
			System.out.println(inputLine);
			System.out.println("Fin de la page.\n");
			in.close();
			
			return nom;
		} catch (IOException e) {
			e.printStackTrace();
			throw new ProblemeChargementPageWeb();
		}
	}
	
	protected String parserLigne(String ligne) throws EchecDuParser, CodeNeCorrespondPasAuCodeDeYahoo, ErreurCodeInconnu {
		CSVParser parser = new CSVParser(',', '"');
		String[] contenu = null;
		
		String codeObtenu = null;
		String nomObtenu = null;
		String infoObtenue = null;
		
		try {
			contenu = parser.parseLine(ligne);
			
			codeObtenu = contenu[0];
			nomObtenu = contenu[1];
			infoObtenue = contenu[2];
		} catch (Exception e) {
			throw new EchecDuParser();
		}
		
		// Si "info" est vide, ça veut dire que le code n'existe pas (en tout cas c'est ce qu'on a compris).
		if (infoObtenue.equals("")) {
			throw new ErreurCodeInconnu();
		}		
		
		// Si le code n'est pas bon c'est qu'il y a un serieux problème !
		if (!this.code.equals(codeObtenu)) {
			throw new CodeNeCorrespondPasAuCodeDeYahoo(this.code, codeObtenu);
		}
		
		return nomObtenu;
	}
	
	private URL creerURL() throws MalformedURLException {
		return new URL("http://download.finance.yahoo.com/d/quotes.csv?s=" + this.code + "&f=" + TAG_NOM_POUR_CODE + "&e=.csv");
	}
	
}

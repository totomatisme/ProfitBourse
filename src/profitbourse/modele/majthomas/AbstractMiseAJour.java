package profitbourse.modele.majthomas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public abstract class AbstractMiseAJour {
	
	/**
	 * le <code>ArrayList</code> qui contient soit les indices, soit les actions à mettre à jour.
	 */
	protected ArrayList<Object> arrayList;
	
	/**
	 * Constructeur de l'objet <code>AbstractMiseAJour</code>.
	 * @param arrayList L'<code>ArrayList </code> qui contient soit les indices, soit les actions à mettre à jour.
	 */
	public AbstractMiseAJour(ArrayList<Object> arrayList) {
		this.arrayList = arrayList;
	}
	
	/**
	 * Effectue l'ensemble du traitement permettant de mettre à jour les indices ou actions contenus dans <code>arrayList</code>.
	 * @throws MiseAJourArrayListVideOuNull
	 * @throws ProblemeChargementPageWeb
	 */
	public void effectuerLaMiseAJour() throws MiseAJourArrayListVideOuNull, ProblemeChargementPageWeb, MalformedURLException, EchecDuParser, CodeNeCorrespondPasAuCodeDeYahoo, NumberFormatException {
		if (this.arrayList == null || this.arrayList.size() == 0) {
			throw new MiseAJourArrayListVideOuNull();
		} else {
			// On crée l'URL qui va nous permettre d'interroger les serveurs de Yahoo
			URL url = this.creerURL();
			
			// On récupère la page Web et on la traite itérativement
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				System.out.println("Nouvelle page yahoo demandée :");
				int indiceArrayListe = 0;
				String inputLine = null;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					this.parserLigne(inputLine, this.arrayList.get(indiceArrayListe));
					indiceArrayListe++;
				}
				System.out.println("Fin de la page.");
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new ProblemeChargementPageWeb();
			}
		}
	}
	
	/**
	 * Crée l'URL qui sera envoyée à Yahoo
	 * @return L'URL qui sera envoyée à Yahoo
	 */
	protected abstract URL creerURL() throws MalformedURLException;
	
	/**
	 * Parse la <code>ligne</code> fournie et effectue la modification de l'objet <code>actionOuIndice</code>.
	 * @param ligne La ligne qui a été renvoyée par Yahoo. Par ex : "MSFT",29.09,"-0.85%","5/31/2012","11:28am"
	 * @param actionOuIndice l'action ou l'indice qui est concerné par cette ligne.
	 */
	protected abstract void parserLigne(String ligne, Object actionOuIndice) throws CodeNeCorrespondPasAuCodeDeYahoo, NumberFormatException, EchecDuParser;

}

class ProblemeChargementPageWeb extends Exception {
	private static final long serialVersionUID = 3732603499614830865L;
}

class MiseAJourArrayListVideOuNull extends Exception {
	private static final long serialVersionUID = -7748211873914794145L;
}

class EchecDuParser extends Exception {
	private static final long serialVersionUID = 8447309210674959240L;
}

class CodeNeCorrespondPasAuCodeDeYahoo extends Exception {
	private static final long serialVersionUID = 32345954516649653L;
}

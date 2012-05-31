package profitbourse.modele.majweb;

import java.io.IOException;
import java.util.ArrayList;

import profitbourse.modele.*;

public class GestionnaireMajWeb {
	
	/**
	 * Fait la mise à jour du cours et de la variation de chaque action de <code>actions</code>
	 * @param actions Les actions à mettre à jour.
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void majActions(Portefeuille portefeuille) throws NumberFormatException, IOException {
		String url = MiseAJourActions.toString(portefeuille);
		MiseAJourActions.getCSV(url);
		MiseAJourActions.miseAJour(portefeuille);
	}
	
	/**
	 * Fait la mise à jour du cours et de la variation de chaque indice de <code>indices</code>
	 * @param indices Les indices à mettre à jour.
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void majIndices(Projet projet) throws NumberFormatException, IOException {
		String url = MiseAJourIndices.toString(projet);
		MiseAJourIndices.getCSV(url);
		MiseAJourIndices.miseAJour(projet);
		
		//peut etre probleme pour appeler le projet
	}
	
	/**
	 * Méthode qui va chercher le nom de l'<code>Action</code> pour le <code>code</code> donné.
	 * @param code Le code (par exemple "EDF.PA").
	 * @return Le nom (par exemple "Electricité de France").
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static String obtenirNomActionPourLeCode(String code) throws NumberFormatException, IOException {
		// Faire pareil que majActions mais on va juste chercher le tag "nom"
		String url = ObtenirNomAction.toString(code);
		ObtenirNomAction.getCSV(url);
		return ObtenirNomAction.donnerNom();
	}
	
	/**
	 * Méthode qui va chercher le nom de l'<code>Indice</code> pour le <code>code</code> donné.
	 * @param code Le code (par exemple "^CAC").
	 * @return Le nom (par exemple "CAC 40").
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static String obtenirNomIndicePourLeCode(String code) throws NumberFormatException, IOException {
		String url = ObtenirNomIndice.toString(code);
		ObtenirNomIndice.getCSV(url);
		return ObtenirNomIndice.donnerNom();
	}
	
}

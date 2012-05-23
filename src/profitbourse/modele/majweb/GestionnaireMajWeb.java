package profitbourse.modele.majweb;

import java.util.ArrayList;

import profitbourse.modele.*;

public class GestionnaireMajWeb {
	
	/**
	 * Fait la mise à jour du cours et de la variation de chaque action de <code>actions</code>
	 * @param actions Les actions à mettre à jour.
	 */
	public static void majActions(ArrayList<Action> actions) {
		
	}
	
	/**
	 * Fait la mise à jour du cours et de la variation de chaque indice de <code>indices</code>
	 * @param indices Les indices à mettre à jour.
	 */
	public static void majIndices(ArrayList<Indice> indices) {
		
	}
	
	/**
	 * Méthode qui va chercher le nom de l'<code>Action</code> pour le <code>code</code> donné.
	 * @param code Le code (par exemple "EDF.PA").
	 * @return Le nom (par exemple "Electricité de France").
	 */
	public static String obtenirNomActionPourLeCode(String code) {
		// Faire pareil que majActions mais on va juste chercher le tag "nom"
		return null;
	}
	
	/**
	 * Méthode qui va chercher le nom de l'<code>Indice</code> pour le <code>code</code> donné.
	 * @param code Le code (par exemple "^CAC").
	 * @return Le nom (par exemple "CAC 40").
	 */
	public static String obtenirNomIndicePourLeCode(String code) {
		return null;
	}
	
}

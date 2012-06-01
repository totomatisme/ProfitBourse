package profitbourse.modele.majthomas;

import java.util.ArrayList;

import profitbourse.modele.*;

public class GestionnaireMajWeb {

	/**
	 * Fait la mise à jour du cours et de la variation de chaque action de <code>actions</code>
	 * @param actions Les actions à mettre à jour.
	 */
	public static void majActions(ArrayList<Action> actions) throws Exception {
		new MiseAJourActions(new ArrayList<Object>(actions)).effectuerLaMiseAJour();
	}

	/**
	 * Fait la mise à jour du cours et de la variation de chaque indice de <code>indices</code>
	 * @param indices Les indices à mettre à jour.
	 */
	public static void majIndices(ArrayList<Indice> indices) throws Exception {
		new MiseAJourIndices(new ArrayList<Object>(indices)).effectuerLaMiseAJour();
	}

	/**
	 * Méthode qui va chercher le nom de l'<code>Action</code> pour le <code>code</code> donné.
	 * @param code Le code (par exemple "EDF.PA").
	 * @return Le nom (par exemple "Electricité de France"), et <code>null</code> si le code n'existe pas !
	 */
	public static String obtenirNomActionPourLeCode(String code) throws Exception {
		try {
			return new TrouverNomPourLeCode(code).recupererLeNomPourLeCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Méthode qui va chercher le nom de l'<code>Indice</code> pour le <code>code</code> donné.
	 * @param code Le code (par exemple "^CAC").
	 * @return Le nom (par exemple "CAC 40"), et <code>null</code> si le code n'existe pas !
	 */
	public static String obtenirNomIndicePourLeCode(String code) throws Exception {
		try {
			return new TrouverNomPourLeCode(code).recupererLeNomPourLeCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
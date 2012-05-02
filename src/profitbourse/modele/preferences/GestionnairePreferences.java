package profitbourse.modele.preferences;

import java.io.File;
import java.util.prefs.*;

public class GestionnairePreferences {

	private static String CHEMIN_SAUVEGARDE = "chemin_sauvegarde";
	private static String DEFAUT_CHEMIN_SAUVEGARDE = System.getProperty("user.home") + File.separator + "profitbourse" + File.separator + "projet.pb";
	
	private static Preferences getPreferences() {
		return Preferences.userNodeForPackage(GestionnairePreferences.class);
	}
	
	public static File getCheminSauvegarde() {
		return new File(getPreferences().get(CHEMIN_SAUVEGARDE, DEFAUT_CHEMIN_SAUVEGARDE));
	}
	
	public static void setCheminSauvegarde(File cheminSauvegarde) {
		getPreferences().put(CHEMIN_SAUVEGARDE, cheminSauvegarde.getPath());
	}
	
	public static File getDossierSauvegarde() {
		File cheminComplet = new File(getPreferences().get(CHEMIN_SAUVEGARDE, DEFAUT_CHEMIN_SAUVEGARDE));
		return cheminComplet.getParentFile();
	}
	
}

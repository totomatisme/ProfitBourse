package profitbourse.modele.preferences;

import java.io.File;
import java.util.Observable;
import java.util.prefs.*;

public class GestionnairePreferences {

	private static NotificationChangementDeFichierSauvegarde notificationChangementDeFichierSauvegarde = new NotificationChangementDeFichierSauvegarde();
	
	private static String DERNIER_PROJET_EXISTE = "dernier_projet_existe";
	private static boolean DEFAUT_DERNIER_PROJET_EXISTE = false;
	
	private static String FICHIER_SAUVEGARDE = "fichier_sauvegarde";
	private static String DEFAUT_FICHIER_SAUVEGARDE = System.getProperty("user.home") + File.separator + "profitbourse" + File.separator + "projet.pb";
	
	private static Preferences getPreferences() {
		return Preferences.userNodeForPackage(GestionnairePreferences.class);
	}
	
	public static File getFichierSauvegarde() {
		return new File(getPreferences().get(FICHIER_SAUVEGARDE, DEFAUT_FICHIER_SAUVEGARDE));
	}
	
	public static void setFichierSauvegarde(File fichierSauvegarde) {
		getPreferences().put(FICHIER_SAUVEGARDE, fichierSauvegarde.getPath());
		notificationChangementDeFichierSauvegarde.notifierChangementDeFichierSauvegarde(fichierSauvegarde);
	}
	
	public static File getDossierSauvegarde() {
		File cheminComplet = new File(getPreferences().get(FICHIER_SAUVEGARDE, DEFAUT_FICHIER_SAUVEGARDE));
		return cheminComplet.getParentFile();
	}
	
	public static boolean getDernierProjetExiste() {
		return getPreferences().getBoolean(DERNIER_PROJET_EXISTE, DEFAUT_DERNIER_PROJET_EXISTE);
	}
	
	public static void setDernierProjetExiste(boolean projetExiste) {
		getPreferences().putBoolean(DERNIER_PROJET_EXISTE, projetExiste);
	}
	
	// OBSERVEURS ET NOTIFIEURS
	
	public static class NotificationChangementDeFichierSauvegarde extends Observable {
		public void notifierChangementDeFichierSauvegarde(File fichierSauvegarde) {
			this.setChanged();
			this.notifyObservers(fichierSauvegarde);
		}
	}
	
	public static NotificationChangementDeFichierSauvegarde getNotificationChangementDeFichierSauvegarde() {
		return GestionnairePreferences.notificationChangementDeFichierSauvegarde;
	}
	
}

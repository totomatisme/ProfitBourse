package profitbourse.modele.preferences;

import java.awt.Color;
import java.io.File;
import java.util.Observable;
import java.util.prefs.*;

public class GestionnairePreferences {

	private static NotificationChangementDeFichierSauvegarde notificationChangementDeFichierSauvegarde = new NotificationChangementDeFichierSauvegarde();
	private static Color LIST_SELECTION_BACKGROUND = javax.swing.UIManager.getDefaults().getColor("List.selectionBackground");
	private static Color LIST_SELECTION_FOREGROUND = javax.swing.UIManager.getDefaults().getColor("List.selectionForeground");
	
	private static String DERNIER_PROJET_EXISTE = "dernier_projet_existe";
	private static boolean DEFAUT_DERNIER_PROJET_EXISTE = false;
	
	private static String FICHIER_SAUVEGARDE = "fichier_sauvegarde";
	private static String DEFAUT_FICHIER_SAUVEGARDE = System.getProperty("user.home") + File.separator + "profitbourse" + File.separator + "projet.pb";
	
	private static String LARGEUR_FENETRE = "largeur_fenetre";
	private static int DEFAUT_LARGEUR_FENETRE = 1024;
	
	private static String HAUTEUR_FENETRE = "hauteur_fenetre";
	private static int DEFAUT_HAUTEUR_FENETRE = 640;
	
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
	
	public static Color getCouleurPositive() {
		return new Color(171, 255, 127);
	}
	
	public static Color getCouleurZero() {
		return null;
	}
	
	public static Color getCouleurNegative() {
		return new Color(255, 182, 170);
	}
	
	public static Color getListSelectionBackground() {
		return LIST_SELECTION_BACKGROUND;
	}
	
	public static Color getListSelectionForeground() {
		return LIST_SELECTION_FOREGROUND;
	}
	
	public static int getHauteurFenetre() {
		return getPreferences().getInt(HAUTEUR_FENETRE, DEFAUT_HAUTEUR_FENETRE);
	}
	
	public static void setHauteurFenetre(int hauteurFenetre) {
		getPreferences().putInt(HAUTEUR_FENETRE, hauteurFenetre);
	}
	
	public static int getLargeurFenetre() {
		return getPreferences().getInt(LARGEUR_FENETRE, DEFAUT_LARGEUR_FENETRE);
	}
	
	public static void setLargeurFenetre(int largeurFenetre) {
		getPreferences().putInt(LARGEUR_FENETRE, largeurFenetre);
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

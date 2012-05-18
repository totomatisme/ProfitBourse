package profitbourse.modele.sauvegarde;

import java.io.*;

import profitbourse.modele.Projet;

public class GestionnaireSauvegarde {
	
	public static void enregistrerProjet(Projet projet, File cheminSauvegarde) throws FileNotFoundException, IOException {
		 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cheminSauvegarde));
		 out.writeObject(projet);
	}
	
	/**
	 * Charge le projet depuis le chemin <code>cheminSauvegarde</code>, et rétabli les attributs et les observeur tel qu'avant la sauvergarde.
	 * @param cheminSauvegarde
	 * @return le projet chargé et initialisé.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Projet chargerProjetDepuisFichier(File cheminSauvegarde) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(cheminSauvegarde));
		Projet projetCharge = (Projet)in.readObject();
		projetCharge.initialisationApresChargement();
		return projetCharge;
	}
	
}

package profitbourse.modele.sauvegarde;

import java.io.*;

import profitbourse.modele.Projet;

public class GestionnaireSauvegarde {
	
	public static void enregistrerProjet(Projet projet, File cheminSauvegarde) throws FileNotFoundException, IOException {
		 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cheminSauvegarde));
		 out.writeObject(projet);
	}
	
	public static Projet chargerProjetDepuisFichier(File cheminSauvegarde) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(cheminSauvegarde));
		return (Projet)in.readObject();
	}
	
}

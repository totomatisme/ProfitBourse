package profitbourse.vue;

import profitbourse.modele.Portefeuille;
import profitbourse.modele.Projet;

public class Controleur {
	
	private Projet projet;
	private Portefeuille portefeuille;
	private ModeleTablePortefeuille modeleTablePortefeuille;
	private FenetrePrincipale fenetrePrincipale;
	
	public Controleur() {
		this.construireInterface();
	}
	
	private void construireInterface() {
		this.fenetrePrincipale = new FenetrePrincipale();
	}

}

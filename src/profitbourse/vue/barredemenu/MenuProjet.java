package profitbourse.vue.barredemenu;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import profitbourse.vue.Controleur;

public class MenuProjet extends JMenu {

	private static final long serialVersionUID = 7127944864466642154L;

	private Controleur controleur;
	
	private MenuItemIntelligent itemNouveauPortefeuille;
	private MenuItemIntelligent itemSupprimerPortefeuille;
	
	private MenuItemIntelligent itemAjouterIndice;
	private MenuItemIntelligent itemSupprimerindice;
	
	public MenuProjet(Controleur controleur) {
		super("Projet");
		this.controleur = controleur;
		
		this.itemNouveauPortefeuille = new MenuItemIntelligent(this.controleur.demandeAjoutPortefeuille);
		this.add(this.itemNouveauPortefeuille);
		
		this.itemSupprimerPortefeuille = new MenuItemIntelligent(this.controleur.demandeSuppressionPortefeuille);
		this.add(this.itemSupprimerPortefeuille);
		
		this.addSeparator();
		
		this.itemAjouterIndice = new MenuItemIntelligent(this.controleur.demandeAjoutIndice);
		this.add(this.itemAjouterIndice);
		
		this.itemSupprimerindice = new MenuItemIntelligent(this.controleur.demandeSuppressionIndice);
		this.add(this.itemSupprimerindice);
	}
	
}

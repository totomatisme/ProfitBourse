package profitbourse.vue.barredemenu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import profitbourse.vue.Controleur;

public class MenuFichier extends JMenu {

	private static final long serialVersionUID = -3501953825689585784L;
	private Controleur controleur;
	private JMenuItem itemNouveauProjet;
	private JMenuItem itemChargerProjet;
	
	private JMenu menuChargerDernierProjet;
	private JMenuItem itemDernierProjet;
	
	private JMenuItem itemEnregistrer;
	private JMenuItem itemEnregistrerSous;
	private JMenuItem itemFermerProjet;
	
	private JMenuItem itemQuitterApplication;
	
	public MenuFichier(Controleur controleur) {
		super("Fichier");
		this.controleur = controleur;
		
		this.itemNouveauProjet = new JMenuItem(this.controleur.demandeNouveauProjet);
		this.add(this.itemNouveauProjet);
		
		this.addSeparator();
		
		this.itemChargerProjet = new JMenuItem(this.controleur.demandeChargerProjet);
		this.add(this.itemChargerProjet);
		
		this.menuChargerDernierProjet = new JMenu("Charger dernier projet");
		this.add(this.menuChargerDernierProjet);
		this.itemDernierProjet = new JMenuItem(this.controleur.demandeChargerDernierProjet);
		this.menuChargerDernierProjet.add(this.itemDernierProjet);
		
		this.addSeparator();
		
		this.itemEnregistrer = new JMenuItem(this.controleur.demandeEnregistrerProjet);
		this.add(this.itemEnregistrer);
		
		this.itemEnregistrerSous = new JMenuItem(this.controleur.demandeEnregistrerProjetSous);
		this.add(this.itemEnregistrerSous);
		
		this.itemFermerProjet = new JMenuItem(this.controleur.demandeFermerProjet);
		this.add(this.itemFermerProjet);
		
		this.addSeparator();
		
		this.itemQuitterApplication = new JMenuItem(this.controleur.demandeQuitterApplication);
		this.add(this.itemQuitterApplication);
	}

}
